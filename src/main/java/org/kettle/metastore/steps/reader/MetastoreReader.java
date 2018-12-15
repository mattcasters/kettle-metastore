package org.kettle.metastore.steps.reader;

import org.pentaho.di.core.Const;
import org.pentaho.di.core.annotations.Step;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.exception.KettleStepException;
import org.pentaho.di.core.row.RowDataUtil;
import org.pentaho.di.core.row.RowMeta;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.BaseStep;
import org.pentaho.di.trans.step.StepDataInterface;
import org.pentaho.di.trans.step.StepInterface;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.step.StepMetaInterface;
import org.pentaho.metastore.api.IMetaStoreAttribute;
import org.pentaho.metastore.api.IMetaStoreElement;
import org.pentaho.metastore.api.IMetaStoreElementType;

import java.util.Date;
import java.util.List;


public class MetastoreReader extends BaseStep implements StepInterface {

  public MetastoreReader( StepMeta stepMeta, StepDataInterface stepDataInterface, int copyNr, TransMeta transMeta, Trans trans ) {
    super( stepMeta, stepDataInterface, copyNr, transMeta, trans );
  }

  @Override public boolean processRow( StepMetaInterface smi, StepDataInterface sdi ) throws KettleException {
    MetastoreReaderMeta meta = (MetastoreReaderMeta) smi;
    MetastoreReaderData data = (MetastoreReaderData) sdi;

    if ( first ) {
      first = false;

      data.metaStore = getMetaStore();

      data.outputRowMeta = new RowMeta();
      meta.getFields( data.outputRowMeta, getStepname(), null, null, this, repository, metaStore );
    }

    try {
      List<String> namespaces = metaStore.getNamespaces();
      log.logBasic( "Exporting "+namespaces.size()+" namespaces, metastore: "+ metaStore.getName() );
      for ( String namespace : namespaces ) {
        List<IMetaStoreElementType> elementTypes = metaStore.getElementTypes( namespace );
        for ( IMetaStoreElementType elementType :  elementTypes) {
          List<String> elementIds = metaStore.getElementIds( namespace, elementType );
          log.logBasic( "  Namespace "+namespace+" with type "+elementType.getName()+" has "+elementIds.size()+" elements");
          for ( String elementId : elementIds ) {
            IMetaStoreElement element = metaStore.getElement( namespace, elementType, elementId );
            writeElementAttributeRows( data.outputRowMeta, elementType.getMetaStoreName(), namespace, elementType.getName(), element.getName(), "/", element, null, 0L );
          }
        }
      }
    } catch ( Exception e ) {
      throw new KettleException( "Error reading from the metastore(s)", e );
    }

    setOutputDone();

    return false;
  }

  /**
   * Write a row for the attribute and it's children
   *
   * @param namespace
   * @param typeName
   * @param elementName
   * @param path
   * @param attribute
   */
  private void writeElementAttributeRows( RowMetaInterface outputRowMeta, String storeName, String namespace, String typeName, String elementName, String path, IMetaStoreAttribute attribute, Long index, Long depth )
    throws KettleStepException {
    String attributePath = path + attribute.getId();
    String attributeValue = getAttributeValueString( attribute.getValue() );
    String attributeType = getAttributeValueType( attribute.getValue() );

    Object[] outputRowData = RowDataUtil.allocateRowData( outputRowMeta.size() );
    int idx = 0;
    outputRowData[ idx++ ] = storeName;
    outputRowData[ idx++ ] = namespace;
    outputRowData[ idx++ ] = typeName;
    outputRowData[ idx++ ] = elementName;
    outputRowData[ idx++ ] = depth;
    outputRowData[ idx++ ] = attributePath;
    outputRowData[ idx++ ] = index;
    outputRowData[ idx++ ] = attributeType;
    outputRowData[ idx++ ] = attributeValue;

    putRow( outputRowMeta, outputRowData );

    // Write children
    //
    for (int i=0;i<attribute.getChildren().size();i++) {
      IMetaStoreAttribute child = attribute.getChildren().get( i );
      writeElementAttributeRows( outputRowMeta, storeName, namespace, typeName, elementName, attributePath+"/", child, Long.valueOf(i), depth+1 );
    }
  }

  private String getAttributeValueString( Object value ) {
    if ( value == null ) {
      return null;
    }
    return value.toString();
  }

  private String getAttributeValueType( Object value ) {
    if ( value == null ) {
      return null;
    }
    if ( value instanceof String ) {
      return "String";
    }
    if ( value instanceof Long || value instanceof Integer ) {
      return "Integer";
    }
    if ( value instanceof Date ) {
      return "Date";
    }
    if ( value instanceof Boolean ) {
      return "Boolean";
    }
    return value.getClass().getName();
  }
}
