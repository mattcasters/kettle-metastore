package org.kettle.metastore.steps.reader;

import org.apache.commons.lang.StringUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.pentaho.di.core.Const;
import org.pentaho.di.i18n.BaseMessages;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.BaseStepMeta;
import org.pentaho.di.trans.step.StepDialogInterface;
import org.pentaho.di.ui.trans.step.BaseStepDialog;

public class MetastoreReaderDialog extends BaseStepDialog implements StepDialogInterface {
  private static Class<?> PKG = MetastoreReaderDialog.class; // for i18n purposes, needed by Translator2!!

  private Text wStepname;

  private Text wStoreNameField;
  private Text wNamespaceField;
  private Text wElementTypeField;
  private Text wElementNameField;
  private Text wAttributeDepthField;
  private Text wAttributePathField;
  private Text wAttributeIndexField;
  private Text wAttributeTypeField;
  private Text wAttributeValueField;
  
  private MetastoreReaderMeta input;

  public MetastoreReaderDialog( Shell parent, Object inputMetadata, TransMeta transMeta, String stepname ) {
    super( parent, (BaseStepMeta) inputMetadata, transMeta, stepname );
    input = (MetastoreReaderMeta) inputMetadata;
  }

  @Override public String open() {
    Shell parent = getParent();
    Display display = parent.getDisplay();

    shell = new Shell( parent, SWT.DIALOG_TRIM | SWT.RESIZE | SWT.MAX | SWT.MIN );
    props.setLook( shell );
    setShellImage( shell, input );

    ModifyListener lsMod = e -> input.setChanged();
    changed = input.hasChanged();

    FormLayout formLayout = new FormLayout();
    formLayout.marginWidth = Const.FORM_MARGIN;
    formLayout.marginHeight = Const.FORM_MARGIN;

    shell.setLayout( formLayout );
    shell.setText( "Metastore Writer" );

    int middle = props.getMiddlePct();
    int margin = Const.MARGIN;

    // Step name line
    //
    Label wlStepname = new Label( shell, SWT.RIGHT );
    wlStepname.setText( "Step name" );
    props.setLook( wlStepname );
    fdlStepname = new FormData();
    fdlStepname.left = new FormAttachment( 0, 0 );
    fdlStepname.right = new FormAttachment( middle, -margin );
    fdlStepname.top = new FormAttachment( 0, margin );
    wlStepname.setLayoutData( fdlStepname );
    wStepname = new Text( shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER );
    props.setLook( wStepname );
    wStepname.addModifyListener( lsMod );
    fdStepname = new FormData();
    fdStepname.left = new FormAttachment( middle, 0 );
    fdStepname.top = new FormAttachment( wlStepname, 0, SWT.CENTER );
    fdStepname.right = new FormAttachment( 100, 0 );
    wStepname.setLayoutData( fdStepname );
    Control lastControl = wStepname;

    Label wlStoreNameField = new Label( shell, SWT.RIGHT );
    wlStoreNameField.setText( "Metastore name field" );
    props.setLook( wlStoreNameField );
    FormData fdlStoreNameField = new FormData();
    fdlStoreNameField.left = new FormAttachment( 0, 0 );
    fdlStoreNameField.right = new FormAttachment( middle, -margin );
    fdlStoreNameField.top = new FormAttachment( lastControl, 2 * margin );
    wlStoreNameField.setLayoutData( fdlStoreNameField );
    wStoreNameField = new Text( shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER );
    props.setLook( wStoreNameField );
    wStoreNameField.addModifyListener( lsMod );
    FormData fdStoreNameField = new FormData();
    fdStoreNameField.left = new FormAttachment( middle, 0 );
    fdStoreNameField.right = new FormAttachment( 100, 0 );
    fdStoreNameField.top = new FormAttachment( wlStoreNameField, 0, SWT.CENTER );
    wStoreNameField.setLayoutData( fdStoreNameField );
    lastControl = wStoreNameField;
    
    Label wlNamespaceField = new Label( shell, SWT.RIGHT );
    wlNamespaceField.setText( "Namespace field" );
    props.setLook( wlNamespaceField );
    FormData fdlNamespaceField = new FormData();
    fdlNamespaceField.left = new FormAttachment( 0, 0 );
    fdlNamespaceField.right = new FormAttachment( middle, -margin );
    fdlNamespaceField.top = new FormAttachment( lastControl, 2 * margin );
    wlNamespaceField.setLayoutData( fdlNamespaceField );
    wNamespaceField = new Text( shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER );
    props.setLook( wNamespaceField );
    wNamespaceField.addModifyListener( lsMod );
    FormData fdNamespaceField = new FormData();
    fdNamespaceField.left = new FormAttachment( middle, 0 );
    fdNamespaceField.right = new FormAttachment( 100, 0 );
    fdNamespaceField.top = new FormAttachment( wlNamespaceField, 0, SWT.CENTER );
    wNamespaceField.setLayoutData( fdNamespaceField );
    lastControl = wNamespaceField;

    Label wlElementTypeField = new Label( shell, SWT.RIGHT );
    wlElementTypeField.setText( "Element type field" );
    props.setLook( wlElementTypeField );
    FormData fdlElementTypeField = new FormData();
    fdlElementTypeField.left = new FormAttachment( 0, 0 );
    fdlElementTypeField.right = new FormAttachment( middle, -margin );
    fdlElementTypeField.top = new FormAttachment( lastControl, 2 * margin );
    wlElementTypeField.setLayoutData( fdlElementTypeField );
    wElementTypeField = new Text( shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER );
    props.setLook( wElementTypeField );
    wElementTypeField.addModifyListener( lsMod );
    FormData fdElementTypeField = new FormData();
    fdElementTypeField.left = new FormAttachment( middle, 0 );
    fdElementTypeField.right = new FormAttachment( 100, 0 );
    fdElementTypeField.top = new FormAttachment( wlElementTypeField, 0, SWT.CENTER );
    wElementTypeField.setLayoutData( fdElementTypeField );
    lastControl = wElementTypeField;

    Label wlElementNameField = new Label( shell, SWT.RIGHT );
    wlElementNameField.setText( "Element name field" );
    props.setLook( wlElementNameField );
    FormData fdlElementNameField = new FormData();
    fdlElementNameField.left = new FormAttachment( 0, 0 );
    fdlElementNameField.right = new FormAttachment( middle, -margin );
    fdlElementNameField.top = new FormAttachment( lastControl, 2 * margin );
    wlElementNameField.setLayoutData( fdlElementNameField );
    wElementNameField = new Text( shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER );
    props.setLook( wElementNameField );
    wElementNameField.addModifyListener( lsMod );
    FormData fdElementNameField = new FormData();
    fdElementNameField.left = new FormAttachment( middle, 0 );
    fdElementNameField.right = new FormAttachment( 100, 0 );
    fdElementNameField.top = new FormAttachment( wlElementNameField, 0, SWT.CENTER );
    wElementNameField.setLayoutData( fdElementNameField );
    lastControl = wElementNameField;

    Label wlAttributeDepthField = new Label( shell, SWT.RIGHT );
    wlAttributeDepthField.setText( "Attribute depth field" );
    props.setLook( wlAttributeDepthField );
    FormData fdlAttributeDepthField = new FormData();
    fdlAttributeDepthField.left = new FormAttachment( 0, 0 );
    fdlAttributeDepthField.right = new FormAttachment( middle, -margin );
    fdlAttributeDepthField.top = new FormAttachment( lastControl, 2 * margin );
    wlAttributeDepthField.setLayoutData( fdlAttributeDepthField );
    wAttributeDepthField = new Text( shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER );
    props.setLook( wAttributeDepthField );
    wAttributeDepthField.addModifyListener( lsMod );
    FormData fdAttributeDepthField = new FormData();
    fdAttributeDepthField.left = new FormAttachment( middle, 0 );
    fdAttributeDepthField.right = new FormAttachment( 100, 0 );
    fdAttributeDepthField.top = new FormAttachment( wlAttributeDepthField, 0, SWT.CENTER );
    wAttributeDepthField.setLayoutData( fdAttributeDepthField );
    lastControl = wAttributeDepthField;

    Label wlAttributePathField = new Label( shell, SWT.RIGHT );
    wlAttributePathField.setText( "Attribute path field" );
    props.setLook( wlAttributePathField );
    FormData fdlAttributePathField = new FormData();
    fdlAttributePathField.left = new FormAttachment( 0, 0 );
    fdlAttributePathField.right = new FormAttachment( middle, -margin );
    fdlAttributePathField.top = new FormAttachment( lastControl, 2 * margin );
    wlAttributePathField.setLayoutData( fdlAttributePathField );
    wAttributePathField = new Text( shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER );
    props.setLook( wAttributePathField );
    wAttributePathField.addModifyListener( lsMod );
    FormData fdAttributePathField = new FormData();
    fdAttributePathField.left = new FormAttachment( middle, 0 );
    fdAttributePathField.right = new FormAttachment( 100, 0 );
    fdAttributePathField.top = new FormAttachment( wlAttributePathField, 0, SWT.CENTER );
    wAttributePathField.setLayoutData( fdAttributePathField );
    lastControl = wAttributePathField;

    Label wlAttributeIndexField = new Label( shell, SWT.RIGHT );
    wlAttributeIndexField.setText( "Attribute index field" );
    props.setLook( wlAttributeIndexField );
    FormData fdlAttributeIndexField = new FormData();
    fdlAttributeIndexField.left = new FormAttachment( 0, 0 );
    fdlAttributeIndexField.right = new FormAttachment( middle, -margin );
    fdlAttributeIndexField.top = new FormAttachment( lastControl, 2 * margin );
    wlAttributeIndexField.setLayoutData( fdlAttributeIndexField );
    wAttributeIndexField = new Text( shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER );
    props.setLook( wAttributeIndexField );
    wAttributeIndexField.addModifyListener( lsMod );
    FormData fdAttributeIndexField = new FormData();
    fdAttributeIndexField.left = new FormAttachment( middle, 0 );
    fdAttributeIndexField.right = new FormAttachment( 100, 0 );
    fdAttributeIndexField.top = new FormAttachment( wlAttributeIndexField, 0, SWT.CENTER );
    wAttributeIndexField.setLayoutData( fdAttributeIndexField );
    lastControl = wAttributeIndexField;

    Label wlAttributeTypeField = new Label( shell, SWT.RIGHT );
    wlAttributeTypeField.setText( "Attribute type field" );
    props.setLook( wlAttributeTypeField );
    FormData fdlAttributeTypeField = new FormData();
    fdlAttributeTypeField.left = new FormAttachment( 0, 0 );
    fdlAttributeTypeField.right = new FormAttachment( middle, -margin );
    fdlAttributeTypeField.top = new FormAttachment( lastControl, 2 * margin );
    wlAttributeTypeField.setLayoutData( fdlAttributeTypeField );
    wAttributeTypeField = new Text( shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER );
    props.setLook( wAttributeTypeField );
    wAttributeTypeField.addModifyListener( lsMod );
    FormData fdAttributeTypeField = new FormData();
    fdAttributeTypeField.left = new FormAttachment( middle, 0 );
    fdAttributeTypeField.right = new FormAttachment( 100, 0 );
    fdAttributeTypeField.top = new FormAttachment( wlAttributeTypeField, 0, SWT.CENTER );
    wAttributeTypeField.setLayoutData( fdAttributeTypeField );
    lastControl = wAttributeTypeField;

    Label wlAttributeValueField = new Label( shell, SWT.RIGHT );
    wlAttributeValueField.setText( "Attribute value field" );
    props.setLook( wlAttributeValueField );
    FormData fdlAttributeValueField = new FormData();
    fdlAttributeValueField.left = new FormAttachment( 0, 0 );
    fdlAttributeValueField.right = new FormAttachment( middle, -margin );
    fdlAttributeValueField.top = new FormAttachment( lastControl, 2 * margin );
    wlAttributeValueField.setLayoutData( fdlAttributeValueField );
    wAttributeValueField = new Text( shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER );
    props.setLook( wAttributeValueField );
    wAttributeValueField.addModifyListener( lsMod );
    FormData fdAttributeValueField = new FormData();
    fdAttributeValueField.left = new FormAttachment( middle, 0 );
    fdAttributeValueField.right = new FormAttachment( 100, 0 );
    fdAttributeValueField.top = new FormAttachment( wlAttributeValueField, 0, SWT.CENTER );
    wAttributeValueField.setLayoutData( fdAttributeValueField );
    lastControl = wAttributeValueField;

    // Some buttons
    wOK = new Button( shell, SWT.PUSH );
    wOK.setText( BaseMessages.getString( PKG, "System.Button.OK" ) );
    wOK.addListener( SWT.Selection, e->ok() );
    wCancel = new Button( shell, SWT.PUSH );
    wCancel.setText( BaseMessages.getString( PKG, "System.Button.Cancel" ) );
    wCancel.addListener( SWT.Selection, e->cancel() );

    // Position the buttons at the bottom of the dialog.
    //
    setButtonPositions( new Button[] { wOK, wCancel }, margin, lastControl );

    // Add listeners
    //
    lsDef = new SelectionAdapter() {
      public void widgetDefaultSelected( SelectionEvent e ) {
        ok();
      }
    };

    wStepname.addSelectionListener( lsDef );
    wStoreNameField.addSelectionListener( lsDef );
    wNamespaceField.addSelectionListener( lsDef );
    wElementTypeField.addSelectionListener( lsDef );
    wElementNameField.addSelectionListener( lsDef );
    wAttributeDepthField.addSelectionListener( lsDef );
    wAttributePathField.addSelectionListener( lsDef );
    wAttributeIndexField.addSelectionListener( lsDef );
    wAttributeValueField.addSelectionListener( lsDef );
    wAttributeTypeField.addSelectionListener( lsDef );

    // Detect X or ALT-F4 or something that kills this window...
    shell.addShellListener( new ShellAdapter() {
      public void shellClosed( ShellEvent e ) {
        cancel();
      }
    } );

    // Set the shell size, based upon previous time...
    //
    setSize();

    getData();
    input.setChanged( changed );

    shell.open();
    while ( !shell.isDisposed() ) {
      if ( !display.readAndDispatch() ) {
        display.sleep();
      }
    }
    return stepname;

  }

  private void cancel() {
    stepname = null;
    input.setChanged( changed );
    dispose();
  }

  public void getData() {
    wStepname.setText( Const.NVL( stepname, "" ) );
    wStoreNameField.setText( Const.NVL( input.getStoreNameField(), "" ) );
    wNamespaceField.setText( Const.NVL( input.getNamespaceField(), "" ) );
    wElementNameField.setText( Const.NVL( input.getElementNameField(), "" ) );
    wElementTypeField.setText( Const.NVL( input.getElementTypeField(), "" ) );
    wAttributeDepthField.setText( Const.NVL( input.getAttributeDepthField(), "" ) );
    wAttributePathField.setText( Const.NVL( input.getAttributePathField(), "" ) );
    wAttributeIndexField.setText( Const.NVL( input.getAttributeIndexField(), "" ) );
    wAttributeTypeField.setText( Const.NVL( input.getAttributeTypeField(), "" ) );
    wAttributeValueField.setText( Const.NVL( input.getAttributeValueField(), "" ) );
  }

  private void ok() {
    if ( StringUtils.isEmpty( wStepname.getText() ) ) {
      return;
    }
    stepname = wStepname.getText(); // return value
    getInfo(input);
    dispose();
  }

  private void getInfo( MetastoreReaderMeta meta) {
    meta.setStoreNameField( wStoreNameField.getText() );
    meta.setNamespaceField( wNamespaceField.getText() );
    meta.setElementTypeField( wElementTypeField.getText() );
    meta.setElementNameField( wElementNameField.getText() );
    meta.setAttributeDepthField( wAttributeDepthField.getText() );
    meta.setAttributePathField( wAttributePathField.getText() );
    meta.setAttributeIndexField( wAttributeIndexField.getText() );
    meta.setAttributeTypeField( wAttributeTypeField.getText() );
    meta.setAttributeValueField( wAttributeValueField.getText() );
  }
}
