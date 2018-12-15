package org.kettle.metastore.steps.reader;

import org.pentaho.di.core.annotations.Step;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.exception.KettleStepException;
import org.pentaho.di.core.exception.KettleXMLException;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.core.row.ValueMetaInterface;
import org.pentaho.di.core.row.value.ValueMetaInteger;
import org.pentaho.di.core.row.value.ValueMetaString;
import org.pentaho.di.core.variables.VariableSpace;
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
  id = "MetastoreReader",
  name = "Metastore Reader",
  description = "Reads from the metastore",
  image = "MetastoreReader.svg",
  categoryDescription = "Input",
  documentationUrl = "https://github.com/mattcasters/kettle-metastore"
)
public class MetastoreReaderMeta extends BaseStepMeta implements StepMetaInterface {

  public static final String STORE_NAME_FIELD = "store_name_field";
  public static final String NAMESPACE_FIELD = "namespace_field";
  public static final String ELEMENT_TYPE_FIELD = "element_type_field";
  public static final String ELEMENT_NAME_FIELD = "element_name_field";
  public static final String ATTRIBUTE_DEPTH_FIELD = "attribute_depth_field";
  public static final String ATTRIBUTE_PATH_FIELD = "attribute_path_field";
  public static final String ATTRIBUTE_TYPE_FIELD = "attribute_type_field";
  public static final String ATTRIBUTE_VALUE_FIELD = "attribute_value_field";
  public static final String ATTRIBUTE_INDEX_FIELD = "attribute_index_field";

  private String storeNameField;
  private String namespaceField;
  private String elementTypeField;
  private String elementNameField;
  private String attributeDepthField;
  private String attributePathField;
  private String attributeIndexField;
  private String attributeTypeField;
  private String attributeValueField;

  public MetastoreReaderMeta() {
    super();
  }

  @Override public void getFields( RowMetaInterface inputRowMeta, String name, RowMetaInterface[] info, StepMeta nextStep, VariableSpace space, Repository repository, IMetaStore metaStore ) {

    inputRowMeta.addValueMeta( new ValueMetaString(storeNameField) );
    inputRowMeta.addValueMeta( new ValueMetaString(namespaceField) );
    inputRowMeta.addValueMeta( new ValueMetaString(elementTypeField) );
    inputRowMeta.addValueMeta( new ValueMetaString(elementNameField) );
    inputRowMeta.addValueMeta( new ValueMetaInteger(attributeDepthField) );
    inputRowMeta.addValueMeta( new ValueMetaString(attributePathField) );
    inputRowMeta.addValueMeta( new ValueMetaInteger(attributeIndexField) );
    inputRowMeta.addValueMeta( new ValueMetaString(attributeTypeField) );
    inputRowMeta.addValueMeta( new ValueMetaString(attributeValueField) );

  }

  @Override public String getXML() {
    StringBuilder xml = new StringBuilder();
    xml.append( XMLHandler.addTagValue( STORE_NAME_FIELD, storeNameField) );
    xml.append( XMLHandler.addTagValue( NAMESPACE_FIELD, namespaceField) );
    xml.append( XMLHandler.addTagValue( ELEMENT_TYPE_FIELD, elementTypeField) );
    xml.append( XMLHandler.addTagValue( ELEMENT_NAME_FIELD, elementNameField) );
    xml.append( XMLHandler.addTagValue( ATTRIBUTE_DEPTH_FIELD, attributeDepthField ) );
    xml.append( XMLHandler.addTagValue( ATTRIBUTE_PATH_FIELD, attributePathField ) );
    xml.append( XMLHandler.addTagValue( ATTRIBUTE_INDEX_FIELD, attributeIndexField ) );
    xml.append( XMLHandler.addTagValue( ATTRIBUTE_TYPE_FIELD, attributeTypeField ) );
    xml.append( XMLHandler.addTagValue( ATTRIBUTE_VALUE_FIELD, attributeValueField ) );
    return xml.toString();
  }

  @Override public void loadXML( Node stepnode, List<DatabaseMeta> databases, IMetaStore metaStore ) {
    storeNameField = XMLHandler.getTagValue( stepnode, STORE_NAME_FIELD);
    namespaceField = XMLHandler.getTagValue( stepnode, NAMESPACE_FIELD);
    elementTypeField = XMLHandler.getTagValue( stepnode, ELEMENT_TYPE_FIELD );
    elementNameField = XMLHandler.getTagValue( stepnode, ELEMENT_NAME_FIELD );
    attributeDepthField = XMLHandler.getTagValue( stepnode, ATTRIBUTE_DEPTH_FIELD );
    attributePathField = XMLHandler.getTagValue( stepnode, ATTRIBUTE_PATH_FIELD );
    attributeIndexField = XMLHandler.getTagValue( stepnode, ATTRIBUTE_INDEX_FIELD );
    attributeTypeField = XMLHandler.getTagValue( stepnode, ATTRIBUTE_TYPE_FIELD );
    attributeValueField = XMLHandler.getTagValue( stepnode, ATTRIBUTE_VALUE_FIELD );
  }

  @Override public void saveRep( Repository rep, IMetaStore metaStore, ObjectId transformationId, ObjectId stepId ) throws KettleException {
    rep.saveStepAttribute( transformationId, stepId, STORE_NAME_FIELD, storeNameField);
    rep.saveStepAttribute( transformationId, stepId, NAMESPACE_FIELD, namespaceField);
    rep.saveStepAttribute( transformationId, stepId, ELEMENT_TYPE_FIELD, elementTypeField );
    rep.saveStepAttribute( transformationId, stepId, ELEMENT_NAME_FIELD, elementNameField );
    rep.saveStepAttribute( transformationId, stepId, ATTRIBUTE_DEPTH_FIELD, attributeDepthField );
    rep.saveStepAttribute( transformationId, stepId, ATTRIBUTE_PATH_FIELD, attributePathField );
    rep.saveStepAttribute( transformationId, stepId, ATTRIBUTE_INDEX_FIELD, attributeIndexField );
    rep.saveStepAttribute( transformationId, stepId, ATTRIBUTE_TYPE_FIELD, attributeTypeField );
    rep.saveStepAttribute( transformationId, stepId, ATTRIBUTE_VALUE_FIELD, attributeValueField );
  }

  @Override public void readRep( Repository rep, IMetaStore metaStore, ObjectId stepId, List<DatabaseMeta> databases ) throws KettleException {
    storeNameField = rep.getStepAttributeString( stepId, STORE_NAME_FIELD);
    namespaceField = rep.getStepAttributeString( stepId, NAMESPACE_FIELD );
    elementTypeField = rep.getStepAttributeString( stepId, ELEMENT_TYPE_FIELD );
    elementNameField = rep.getStepAttributeString( stepId, ELEMENT_NAME_FIELD );
    attributeDepthField = rep.getStepAttributeString( stepId, ATTRIBUTE_DEPTH_FIELD );
    attributePathField = rep.getStepAttributeString( stepId, ATTRIBUTE_PATH_FIELD );
    attributeIndexField = rep.getStepAttributeString( stepId, ATTRIBUTE_INDEX_FIELD );
    attributeTypeField = rep.getStepAttributeString( stepId, ATTRIBUTE_TYPE_FIELD );
    attributeValueField = rep.getStepAttributeString( stepId, ATTRIBUTE_VALUE_FIELD );
  }

  @Override public void setDefault() {
    storeNameField="storeName";
    namespaceField="namespace";
    elementTypeField="elementType";
    elementNameField="element";
    attributeDepthField="depth";
    attributePathField="path";
    attributeIndexField="index";
    attributeTypeField="type";
    attributeValueField="value";
  }

  @Override public StepInterface getStep( StepMeta stepMeta, StepDataInterface stepDataInterface, int copyNr, TransMeta transMeta, Trans trans ) {
    return new MetastoreReader( stepMeta, stepDataInterface, copyNr, transMeta, trans );
  }

  @Override public StepDataInterface getStepData() {
    return new MetastoreReaderData();
  }

  @Override public String getDialogClassName() {
    return MetastoreReaderDialog.class.getName();
  }

  /**
   * Gets storeNameField
   *
   * @return value of storeNameField
   */
  public String getStoreNameField() {
    return storeNameField;
  }

  /**
   * @param storeNameField The storeNameField to set
   */
  public void setStoreNameField( String storeNameField ) {
    this.storeNameField = storeNameField;
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
   * Gets attributeDepthField
   *
   * @return value of attributeDepthField
   */
  public String getAttributeDepthField() {
    return attributeDepthField;
  }

  /**
   * @param attributeDepthField The attributeDepthField to set
   */
  public void setAttributeDepthField( String attributeDepthField ) {
    this.attributeDepthField = attributeDepthField;
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

  /**
   * Gets attributeTypeField
   *
   * @return value of attributeTypeField
   */
  public String getAttributeTypeField() {
    return attributeTypeField;
  }

  /**
   * @param attributeTypeField The attributeTypeField to set
   */
  public void setAttributeTypeField( String attributeTypeField ) {
    this.attributeTypeField = attributeTypeField;
  }
}
