package org.kettle.metastore.steps.reader;

import org.pentaho.di.core.row.RowMeta;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.trans.step.BaseStepData;
import org.pentaho.di.trans.step.StepDataInterface;
import org.pentaho.metastore.api.IMetaStore;

public class MetastoreReaderData extends BaseStepData implements StepDataInterface {
  public IMetaStore metaStore;
  public int namespaceFieldIndex;
  public int elementTypeFieldIndex;
  public int elementNameFieldIndex;
  public int attributePathFieldIndex;
  public int attributeIndexFieldIndex;
  public int attributeValueFieldIndex;
  public RowMetaInterface outputRowMeta;
}
