package org.kettle.metastore.steps.writer;

import org.pentaho.di.core.annotations.Step;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.exception.KettleXMLException;
import org.pentaho.di.core.xml.XMLHandler;
import org.pentaho.di.repository.ObjectId;
import org.pentaho.di.repository.Repository;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.BaseStepMeta;
import org.pentaho.di.trans.step.StepDataInterface;
import org.pentaho.di.trans.step.StepInterface;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.step.StepMetaInterface;
import org.pentaho.metastore.api.IMetaStore;
import org.w3c.dom.Node;

import java.util.List;

@Step(
  id = "MetastoreWriter",
  name = "Metastore Writer",
  description = "Writes to the metastore",
  image = "MetastoreWriter.svg",
  categoryDescription = "Output",
  documentationUrl = "https://github.com/mattcasters/kettle-metastore"
)
public class MetastoreWriterMeta extends BaseStepMeta implements StepMetaInterface {

  public static final String NAMESPACE_FIELD = "namespace_field";
  public static final String ELEMENT_TYPE_FIELD = "element_type_field";
  public static final String ELEMENT_NAME_FIELD = "element_name_field";
  public static final String ATTRIBUTE_PATH_FIELD = "attribute_path_field";
  public static final String ATTRIBUTE_INDEX_FIELD = "attribute_index_field";
  public static final String ATTRIBUTE_VALUE_FIELD = "attribute_value_field";

  private String namespaceField;
  private String elementTypeField;
  private String elementNameField;
  private String attributePathField;
  private String attributeIndexField;
  private String attributeValueField;

  public MetastoreWriterMeta() {
    super();
  }

  @Override public String getXML() throws KettleException {
    StringBuilder xml = new StringBuilder();
    xml.append( XMLHandler.addTagValue( NAMESPACE_FIELD, namespaceField) );
    xml.append( XMLHandler.addTagValue( ELEMENT_TYPE_FIELD, elementTypeField) );
    xml.append( XMLHandler.addTagValue( ELEMENT_NAME_FIELD, elementNameField) );
    xml.append( XMLHandler.addTagValue( ATTRIBUTE_PATH_FIELD, attributePathField ) );
    xml.append( XMLHandler.addTagValue( ATTRIBUTE_INDEX_FIELD, attributeIndexField ) );
    xml.append( XMLHandler.addTagValue( ATTRIBUTE_VALUE_FIELD, attributeValueField ) );
    return xml.toString();
  }

  @Override public void loadXML( Node stepnode, List<DatabaseMeta> databases, IMetaStore metaStore ) throws KettleXMLException {
    namespaceField = XMLHandler.getTagValue( stepnode, NAMESPACE_FIELD);
    elementTypeField = XMLHandler.getTagValue( stepnode, ELEMENT_TYPE_FIELD );
    elementNameField = XMLHandler.getTagValue( stepnode, ELEMENT_NAME_FIELD );
    attributePathField = XMLHandler.getTagValue( stepnode, ATTRIBUTE_PATH_FIELD );
    attributeIndexField = XMLHandler.getTagValue( stepnode, ATTRIBUTE_INDEX_FIELD );
    attributeValueField = XMLHandler.getTagValue( stepnode, ATTRIBUTE_VALUE_FIELD );
  }

  @Override public void saveRep( Repository rep, IMetaStore metaStore, ObjectId transformationId, ObjectId stepId ) throws KettleException {
    rep.saveStepAttribute( transformationId, stepId, NAMESPACE_FIELD, namespaceField);
    rep.saveStepAttribute( transformationId, stepId, ELEMENT_TYPE_FIELD, elementTypeField );
    rep.saveStepAttribute( transformationId, stepId, ELEMENT_NAME_FIELD, elementNameField );
    rep.saveStepAttribute( transformationId, stepId, ATTRIBUTE_PATH_FIELD, attributePathField );
    rep.saveStepAttribute( transformationId, stepId, ATTRIBUTE_INDEX_FIELD, attributeIndexField );
    rep.saveStepAttribute( transformationId, stepId, ATTRIBUTE_VALUE_FIELD, attributeValueField );
  }

  @Override public void readRep( Repository rep, IMetaStore metaStore, ObjectId stepId, List<DatabaseMeta> databases ) throws KettleException {
    namespaceField = rep.getStepAttributeString( stepId, NAMESPACE_FIELD );
    elementTypeField = rep.getStepAttributeString( stepId, ELEMENT_TYPE_FIELD );
    elementNameField = rep.getStepAttributeString( stepId, ELEMENT_NAME_FIELD );
    attributePathField = rep.getStepAttributeString( stepId, ATTRIBUTE_PATH_FIELD );
    attributeIndexField = rep.getStepAttributeString( stepId, ATTRIBUTE_INDEX_FIELD );
    attributeValueField = rep.getStepAttributeString( stepId, ATTRIBUTE_VALUE_FIELD );
  }

  @Override public void setDefault() {
  }

  @Override public StepInterface getStep( StepMeta stepMeta, StepDataInterface stepDataInterface, int copyNr, TransMeta transMeta, Trans trans ) {
    return new MetastoreWriter( stepMeta, stepDataInterface, copyNr, transMeta, trans );
  }

  @Override public StepDataInterface getStepData() {
    return new MetastoreWriterData();
  }

  /**
   * Gets namespaceField
   *
   * @return value of namespaceField
   */
  public String getNamespaceField() {
    return namespaceField;
  }

  /**
   * @param namespaceField The namespaceField to set
   */
  public void setNamespaceField( String namespaceField ) {
    this.namespaceField = namespaceField;
  }

  /**
   * Gets elementTypeField
   *
   * @return value of elementTypeField
   */
  public String getElementTypeField() {
    return elementTypeField;
  }

  /**
   * @param elementTypeField The elementTypeField to set
   */
  public void setElementTypeField( String elementTypeField ) {
    this.elementTypeField = elementTypeField;
  }

  /**
   * Gets elementNameField
   *
   * @return value of elementNameField
   */
  public String getElementNameField() {
    return elementNameField;
  }

  /**
   * @param elementNameField The elementNameField to set
   */
  public void setElementNameField( String elementNameField ) {
    this.elementNameField = elementNameField;
  }

  /**
   * Gets attributePathField
   *
   * @return value of attributePathField
   */
  public String getAttributePathField() {
    return attributePathField;
  }

  /**
   * @param attributePathField The attributePathField to set
   */
  public void setAttributePathField( String attributePathField ) {
    this.attributePathField = attributePathField;
  }

  /**
   * Gets attributeIndexField
   *
   * @return value of attributeIndexField
   */
  public String getAttributeIndexField() {
    return attributeIndexField;
  }

  /**
   * @param attributeIndexField The attributeIndexField to set
   */
  public void setAttributeIndexField( String attributeIndexField ) {
    this.attributeIndexField = attributeIndexField;
  }

  /**
   * Gets attributeValueField
   *
   * @return value of attributeValueField
   */
  public String getAttributeValueField() {
    return attributeValueField;
  }

  /**
   * @param attributeValueField The attributeValueField to set
   */
  public void setAttributeValueField( String attributeValueField ) {
    this.attributeValueField = attributeValueField;
  }
}
