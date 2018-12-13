package org.kettle.metastore.steps.writer;

import org.pentaho.di.trans.step.BaseStepData;
import org.pentaho.di.trans.step.StepDataInterface;
import org.pentaho.metastore.api.IMetaStore;

public class MetastoreWriterData extends BaseStepData implements StepDataInterface {
  public IMetaStore metaStore;
  public int namespaceFieldIndex;
  public int elementTypeFieldIndex;
  public int elementNameFieldIndex;
  public int attributePathFieldIndex;
  public int attributeIndexFieldIndex;
  public int attributeValueFieldIndex;
}
