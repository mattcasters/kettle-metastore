package org.kettle.metastore.steps.writer;

import org.pentaho.di.core.Const;
import org.pentaho.di.core.exception.KettleException;
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

public class MetastoreWriter extends BaseStep implements StepInterface {

  public MetastoreWriter( StepMeta stepMeta, StepDataInterface stepDataInterface, int copyNr, TransMeta transMeta, Trans trans ) {
    super( stepMeta, stepDataInterface, copyNr, transMeta, trans );
  }

  @Override public boolean processRow( StepMetaInterface smi, StepDataInterface sdi ) throws KettleException {
    MetastoreWriterMeta meta = (MetastoreWriterMeta) smi;
    MetastoreWriterData data = (MetastoreWriterData) sdi;

    Object[] row = getRow();
    if (row==null) {
      setOutputDone();
      return false;
    }
    if (first) {
      first = false;

      data.metaStore = getMetaStore();

      data.namespaceFieldIndex = getInputRowMeta().indexOfValue( meta.getNamespaceField() );
      if (data.namespaceFieldIndex<0) {
        throw new KettleException( "Unable to find namespace field '"+meta.getElementTypeField()+"' in the input rows" );
      }
      data.elementTypeFieldIndex = getInputRowMeta().indexOfValue( meta.getElementTypeField() );
      if (data.elementTypeFieldIndex<0) {
        throw new KettleException( "Unable to find element type field '"+meta.getElementTypeField()+"' in the input rows" );
      }
      data.elementNameFieldIndex = getInputRowMeta().indexOfValue( meta.getElementNameField() );
      if (data.elementNameFieldIndex<0) {
        throw new KettleException( "Unable to find element name field '"+meta.getElementTypeField()+"' in the input rows" );
      }
      data.attributePathFieldIndex = getInputRowMeta().indexOfValue( meta.getAttributePathField() );
      if (data.attributePathFieldIndex<0) {
        throw new KettleException( "Unable to find attribute path field '"+meta.getElementTypeField()+"' in the input rows" );
      }
      data.attributeIndexFieldIndex = getInputRowMeta().indexOfValue( meta.getAttributeIndexField() );
      if (data.attributeIndexFieldIndex<0) {
        throw new KettleException( "Unable to find attribute index field '"+meta.getElementTypeField()+"' in the input rows" );
      }
      data.attributeValueFieldIndex = getInputRowMeta().indexOfValue( meta.getAttributeValueField() );
      if (data.attributeValueFieldIndex<0) {
        throw new KettleException( "Unable to find attribute value field '"+meta.getElementTypeField()+"' in the input rows" );
      }
    }
    String namespace = getInputRowMeta().getString( row, data.namespaceFieldIndex );
    String elementTypeName = getInputRowMeta().getString( row, data.elementTypeFieldIndex );
    String elementName = getInputRowMeta().getString( row, data.elementNameFieldIndex );
    String attributePath = getInputRowMeta().getString( row, data.attributePathFieldIndex );
    String attributeIndexes = getInputRowMeta().getString( row, data.attributeIndexFieldIndex );
    Object attributeValue = getInputRowMeta().getValueMeta( data.attributeValueFieldIndex ).getNativeDataType( row[data.attributeValueFieldIndex] );

    try {

      // Get the element type to update or create
      //
      IMetaStoreElementType elementType = data.metaStore.getElementType( namespace, elementTypeName );
      if (elementType==null) {
        // Create the element type if it doesn't exist
        //
        elementType = data.metaStore.newElementType( namespace );
        elementType.setName( elementTypeName );
        elementType.setDescription( elementTypeName );
        data.metaStore.createElementType( namespace, elementType );
      }

      // Get the element to update or create
      //
      IMetaStoreElement element = data.metaStore.getElement( namespace, elementType, elementName );
      if (element==null) {
        element = data.metaStore.newElement();
        element.setElementType( elementType );
        element.setName( elementName );
      }

      // What is the path to update?
      //
      String[] attributes = attributePath.split( "/" );
      String[] indexes = attributeIndexes.split( "/" );
      IMetaStoreAttribute attribute = element;
      for (int i=0;i<attributes.length;i++) {
        int childIndex = i<indexes.length ? Const.toInt(indexes[i], -1) : -1;
        if (childIndex<0) {
          IMetaStoreAttribute child = attribute.getChild( attributes[ i ] );
          if ( child == null ) {
            // If it doesn't exist, create it!
            //
            IMetaStoreAttribute newAttr = data.metaStore.newAttribute( attributes[ i ], null );
            attribute = child;
          }
        } else {
          while (childIndex>=attribute.getChildren().size()) {
            IMetaStoreAttribute child = data.metaStore.newAttribute( Integer.toString( attribute.getChildren().size() ), null );
            attribute.getChildren().add(child);
          }
          IMetaStoreAttribute child = attribute.getChildren().get( childIndex );
          data.metaStore.newAttribute( attributes[ i ], null );
          attribute = child;
        }
      }
      // Set the selected attribute
      //
      attribute.setValue( attributeValue );

      // Save the element after every attribute update.  This is slow but working for now.
      // TODO: cache element type and element name and see if it's the same as the last row
      //       Then keep type and element in memory and only save on a change of input.
      //
      data.metaStore.updateElement( namespace, elementType, elementName, element );
    } catch(Exception e) {
      throw new KettleException( "Error updating attribute", e);
    }

    // Simply pass the input row to the next steps
    //
    putRow( getInputRowMeta(), row );

    return true;
  }
}
