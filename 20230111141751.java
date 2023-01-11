package com.hcsaastech.ehis.doc;

import com.changing.hca.hcaapi.Base64Utils;

import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPConnectMode;
import com.enterprisedt.net.ftp.FTPMessageCollector;
import com.enterprisedt.net.ftp.FTPTransferType;

import com.hcsaastech.ehis.doc.common.DateChooserButton;
import com.hcsaastech.ehis.doc.common.GlobalParameters;
import com.hcsaastech.ehis.doc.common.HCBinding;
import com.hcsaastech.ehis.doc.common.HCComboBoxItem;
import com.hcsaastech.ehis.doc.common.HCComboBoxModel;
import com.hcsaastech.ehis.doc.common.HCTable;
import com.hcsaastech.ehis.doc.common.HCTableModel;
import com.hcsaastech.ehis.doc.common.MessageManager;
import com.hcsaastech.ehis.doc.dao.EmrIpdEncounterDAO;
import com.hcsaastech.ehis.doc.dao.EmrNoteCategoryDAO;
import com.hcsaastech.ehis.doc.dao.EmrNoteChapterDAO;
import com.hcsaastech.ehis.doc.dao.EmrNoteDataobjectDAO;
import com.hcsaastech.ehis.doc.dao.EmrPatNoteDAO;
import com.hcsaastech.ehis.doc.dao.EmrPatNoteLogDAO;
import com.hcsaastech.ehis.doc.dao.EmrSheetDefinitionDAO;
import com.hcsaastech.ehis.doc.dao.EmrTempDischarge2DAO;
import com.hcsaastech.ehis.doc.dao.IerOrderDAO;
import com.hcsaastech.ehis.doc.dao.IerPatDiagDAO;
import com.hcsaastech.ehis.doc.dao.SecUserDAO;
import com.hcsaastech.ehis.doc.dialog.DeptLovDialog;
import com.hcsaastech.ehis.doc.dialog.DittoDialog;
import com.hcsaastech.ehis.doc.dialog.EmrFamilyTreeDialog;
import com.hcsaastech.ehis.doc.dialog.EmrPatNoteErrlogDialog;
import com.hcsaastech.ehis.doc.dialog.GetDocInfoDialog;
import com.hcsaastech.ehis.doc.dialog.GetTranBedInfoDialog;
import com.hcsaastech.ehis.doc.dialog.NotePhraseDialog;
import com.hcsaastech.ehis.doc.dialog.PrinterLocationDialog;
import com.hcsaastech.ehis.doc.dialog.ProgressNotePrintDialog;
import com.hcsaastech.ehis.doc.dialog.StationLovDialog;
import com.hcsaastech.ehis.doc.dialog.VsLovDialog;
import com.hcsaastech.ehis.doc.hcaemr.AdminNoteEmrSheet;
import com.hcsaastech.ehis.doc.hcaemr.DischgNoteEmrPdfSheet;
import com.hcsaastech.ehis.doc.hcaemr.OpNoteEmrPdfSheet;
import com.hcsaastech.ehis.doc.hcaemr.ProgressNoteEmrSheet;
import com.hcsaastech.ehis.doc.report.AdminNoteReport;
import com.hcsaastech.ehis.doc.report.BaseReport;
import com.hcsaastech.ehis.doc.report.ChtDisChgReport;
import com.hcsaastech.ehis.doc.report.DisChgReport;
import com.hcsaastech.ehis.doc.report.NoteReport;
import com.hcsaastech.ehis.doc.report.OrReport;
import com.hcsaastech.ehis.doc.report.ProgressNoteReport;
import com.hcsaastech.ehis.doc.report.TreNoteReport;
import com.hcsaastech.ehis.doc.utils.DBConnectionHelper;
import com.hcsaastech.ehis.doc.utils.DateUtil;
import com.hcsaastech.ehis.doc.utils.FunctionUtil;
import com.hcsaastech.ehis.doc.utils.HTMLHandler;
import com.hcsaastech.ehis.doc.utils.ImageUtil;
import com.hcsaastech.ehis.doc.utils.MySeqGenerator;
import com.hcsaastech.ehis.doc.utils.ReportUtil;
import com.hcsaastech.ehis.doc.utils.StringUtil;
import com.hcsaastech.ehis.doc.utils.UIUtils;
import com.hcsaastech.ehis.doc.vo.EmrIpdEncounterVO;
import com.hcsaastech.ehis.doc.vo.EmrNoteCategoryVO;
import com.hcsaastech.ehis.doc.vo.EmrNoteChapterVO;
import com.hcsaastech.ehis.doc.vo.EmrPatNoteLogVO;
import com.hcsaastech.ehis.doc.vo.EmrPatNoteVO;
import com.hcsaastech.ehis.doc.vo.EmrSheetDefinitionVO;
import com.hcsaastech.ehis.doc.vo.EmrTempDischarge2VO;
import com.hcsaastech.ehis.doc.vo.IerOrderVO;
import com.hcsaastech.ehis.doc.vo.IerPatDiagVO;
import com.hcsaastech.ehis.doc.vo.OrDataVO;
import com.hcsaastech.ehis.doc.vo.SecUserVO;

import com.hexidec.ekit.HTMLViewer;
import com.hexidec.util.Utils;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import com.lowagie.text.xml.simpleparser.SimpleXMLParser;

import info.clearthought.layout.TableLayout;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.math.BigDecimal;

import java.net.InetAddress;
import java.net.UnknownHostException;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.ListModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import net.htmlparser.jericho.Source;

import notews.DocDataObjectStub;
import notews.ResultDTO;

import notews.report.RepBasStub;

import oracle.jdbc.OracleTypes;

import org.apache.log4j.Logger;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

/**funtion list
 * addBtn.addActionListener �s�W�f��
 * changeTextPanel:����TAB���椧�ʧ@
 * checkOutDocument:����currentVO���s���v��
 * checkInDocument:����currentVO���s���v��
 * doEmrSheet:�e����ñ�����x
 * directToDiagPage
 * delBtn.addActionListener �R���f��
 * getIPAddress:���oIP��}
 * getDutyDocInfo:���oICU��Z��v
 * getCommNeedFlagByDate:�]�ַ��PROGRESS NOTE�O�_�ݭn�QCOMMAND
 * getRptdbFlag:���o�O�_�eñ�|��ñ��FLAG
 * limitsOfSave_2:�������]��
 * limitsOfSave_1:�Ȧs���]��
 * limitsOfSave:�s�ɮɽ]�ָӯf���O�_�i�H�Q����
 * loadPatNoteData:Ū��EMR_PAT_NOTE���
 * loadEmrPatList
 * rollBack:�_��
 * saveData(String saveStatus): 1.�Ȧs  2.����
 * tempSave:�Ȧs
 * saveEmrInterfaceTable:�X�K�~���eñ
 * doDiffPdf:�������
 * directToDiagPage:�E�_���@
 * */

@SuppressWarnings("serial")
public class NoteMainPanel extends JPanel
{
    public String loadEmrPatListF = "loadEmrPatList";
    
    public EmrIpdEncounterVO emrIpdEncounterVO; //for �妸
	
    private Logger logger = Logger.getLogger(getClass());
    
    private JFrame sourceFrame;
  
    private BorderLayout borderLayout = new BorderLayout();
    private JPanel jPanel1 = new JPanel();
    private JPanel jPanel2 = new JPanel();
    private JPanel jPanel3 = new JPanel();
    private JPanel jPanel4 = new JPanel(); //�f�w�d��
    private JPanel jPanel5 = new JPanel(); //�ť�Panel
    private JPanel mPanel = new JPanel();
    private JLabel eventTimeLabel = new JLabel("�f�����:");
    private JTextField eventTimeTextField = new JTextField();
    private DateChooserButton dcb1 = new DateChooserButton(eventTimeTextField);
    
    private FormLayout formLayout1 = new FormLayout("f:3dlu:n, f:315px:n, f:3dlu:n, f:794px:n, f:p:n", "c:3dlu:n, c:m:g, c:3dlu:n");    
    
    private JSplitPane jSplitPane1 = new JSplitPane();  //���`����
    private JSplitPane jSplitPane2 = new JSplitPane();  //�f���M��
    private JSplitPane jSplitPane3 = new JSplitPane();  //�f�w�M��
    
    private JPanel outsideNoteEkitPanel = new JPanel();
    
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JScrollPane jScrollPane2 = new JScrollPane();   //�f�w�M��
    private JScrollPane jScrollPane3 = new JScrollPane();
    
    private HCTable emrPatNoteTable = new HCTable()
    {
        public boolean isCellEditable(int row, int col)
        {
            return false;
        }
    };
    
    private HCTable emrPatListTable = new HCTable()
    {
        public boolean isCellEditable(int row, int col)
        {
            return false;
        }
        
//        public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
//            Component component = super.prepareRenderer(renderer, row, column);
//            
//            return component;
//        }
    };
           
    private JTabbedPane jideTabbedPane = new JTabbedPane();
    private JTabbedPane jideTabbedPane1 = new JTabbedPane();
    private JList jList1 = new JList();
    
    //private NoteMainPanel noteMainPanel = this;
    //update by leon 2012-06-05:�W�[VS�d�����ҡA��n�J�̻P�w�̤�VS�����P�H�����
    private NoteTemplatePanel noteTemplatePanel;
    private NoteTemplatePanel noteTemplateVsPanel;
    private NoteTemplatePanel noteTemplateHosPanel;
    private NoteDataObjectPanel noteDataObjectPanel;
    
    private NoteEkitPanel noteEkitPanel = new NoteEkitPanel();
  
    private HTMLViewer htmlViewer = new HTMLViewer();
    private TreeMap hm = new TreeMap();
    private TreeMap chapterHm = new TreeMap();
    
    private TreeMap<String, String> noteFormTitleHM = new TreeMap<String, String>();
    
    private String filterString = null;
    private String oldSeledtedItem = null;
    private int oldSeledtedIndex = 0;    
    private HTMLHandler handler = new HTMLHandler();
    private CardLayout cardLayout = new CardLayout();
  
    private JComboBox noteCategoryComboBox = new JComboBox();
    
    private JButton addBtn = new JButton(new ImageIcon(this.getClass().getResource("/images/add_24.png")));
    private JButton delBtn = new JButton(new ImageIcon(this.getClass().getResource("/images/remov_24.png")));    
    private JButton tmpBtn = new JButton("�Ȧs",new ImageIcon(this.getClass().getResource("/images/savas_24.png")));    
    private JButton savBtn = new JButton("����",new ImageIcon(this.getClass().getResource("/images/save_24.png"))); 
    
    //private JButton tmpBtn = new JButton("�Ȧs");
    //private JButton senBtn = new JButton("�e��");
    //private JButton retBtn = new JButton("�h�^");
    //private JButton savBtn = new JButton("����");    
    
    
    private JButton rbkBtn = new JButton(new ImageIcon(this.getClass().getResource("/images/ref_24.png")));    
    
    private JButton logButton = new JButton("LogIt");
    
    private JButton queryButton = new JButton("�d��");        
    private JButton queryButton1 = new JButton("�d�ߤj��5��");
    private JButton queryButton2 = new JButton("�d�ߤj��3��");    
    private JButton queryButton3 = new JButton("�d��3�ѥH��");
    
    
    private JButton dittoButton = new JButton("DITTO",new ImageIcon(this.getClass().getResource("/images/ditto.png")));    
    private JButton phraseButton = new JButton("���J���y",new ImageIcon(this.getClass().getResource("/images/phrase.png")));
    private JButton previewPrintButton = new JButton("�w���C�L",new ImageIcon(this.getClass().getResource("/images/preview.png")));
    private JButton defaultValueButton = new JButton("�w�]�d��",new ImageIcon(this.getClass().getResource("/images/tmpl.png")));    
    private JButton fourceEditButton = new JButton("�j��s��",new ImageIcon(this.getClass().getResource("/images/force.png")));
    private JButton directDiagButton = new JButton("�E�_���@",new ImageIcon(this.getClass().getResource("/images/diag.png")));
    private JButton checkChartButton = new JButton("�ʺ|�f��",new ImageIcon(this.getClass().getResource("/images/loss.png")));
    
    private JButton hcaEmrButton = new JButton("�ɰeñ��",new ImageIcon(this.getClass().getResource("/images/sign.png")));
    private JButton printButton = new JButton("�ȥ��C�L",new ImageIcon(this.getClass().getResource("/images/paper.png")));    
    private JButton diffPdfButton = new JButton("�������",new ImageIcon(this.getClass().getResource("/images/ver.png")));
    private JButton progressAllButton = new JButton("���{�`ñ",new ImageIcon(this.getClass().getResource("/images/sign.png")));
    private JButton hcaEmrOpButton = new JButton("�ɰeñ��(��N)",new ImageIcon(this.getClass().getResource("/images/sign.png")));
    private JButton pedigreeButton = new JButton("�a�ھ�",new ImageIcon(this.getClass().getResource("/images/tree.png")));    
        
    private ArrayList emrNoteChapterData = null;
    public EmrPatNoteVO currentVO = null;    
    
    private ImageUtil imageUtil = new ImageUtil();
    private ArrayList chapterOrderArray = new ArrayList();
    
    private HCBinding binding = new HCBinding("com.hcsaastech.ehis.doc.vo.EmrPatNoteVO");
    
    private JTextField txtEncounterNo = new JTextField("");
    private JTextField txtChartNo = new JTextField("");
    private JTextField txtName = new JTextField("");
    private JTextField txtAge = new JTextField("");
    private JTextField txtBirthDate = new JTextField("");
    private JTextField txtBedNo = new JTextField("");
    private JTextField txtDeptName = new JTextField("");
    private JTextField txtVsName = new JTextField("");
    private JTextField txtAdmitDate = new JTextField("");
    private JTextField txtSex = new JTextField("");
    private JTextField txtBranchCode = new JTextField("");
    
    private JLabel lblBranchCode = new JLabel("�@�z��:");
    private JLabel lblTreNoteName = new JLabel("�B�m�W��:");
    
    private JLabel lbldtlKind = new JLabel("�l������:");
    
    private JLabel lblPersonInCharge = new JLabel("��ñ����v:");
    private JTextField txtPersonInChargeName = new JTextField("");
    private JTextField txtPersonInChargeCode = new JTextField("");
    private JButton btnPersonInCharge = new JButton("...");
    private JButton btnQueryDept = new JButton("...");
    private JButton btnQueryVs = new JButton("...");
    private JButton btnQueryStation = new JButton("...");
    
    private JComboBox treNoteComboBox = new JComboBox();
    
    //update by jeff:���{�O���Ӥ��l���O
    private String[] wkSummaryComboBoxShowData = {"Progress Note", "Weekly Summary",
    		"Transfer Note", "On Service Note", "Duty Note"};    
    private JComboBox wkSummaryComboBox = new JComboBox(wkSummaryComboBoxShowData);
    
    //update by leon 2014-02-18:�X�|�K�n�Ӥ��l���O
    //private String[] dischgNoteComboBoxShowData = {"Dischg Summary", "Transfer Summary","Cut Summary"};
    private String[] dischgNoteComboBoxShowData = {"D:�X�|�K�n", "T:���K�n","C:���q���M�K�n"};   
    private JComboBox dischgNoteComboBox = new JComboBox(dischgNoteComboBoxShowData);    
    
    private JTextField deptCodeField = new JTextField("");
    private JTextField vsCodeField = new JTextField("");
    private JTextField branchCodeField = new JTextField("");
    private JTextField encounterNoField = new JTextField("");
    private JTextField chartNoField = new JTextField("");
    private JTextField patNameField = new JTextField("");
    private JTextField bedNoField = new JTextField("");
    private JTextField admitDateField = new JTextField("");
    private DateChooserButton dcb_admitDate = new DateChooserButton(admitDateField, "yyyy-MM-dd");
    private JTextField txtDischgDays = new JTextField("15");
    
    private JLabel lblDeptName = new JLabel("");
    private JLabel lblVsName = new JLabel("");
    private JLabel lblStationName = new JLabel("");
    private JLabel lblDischgDays = new JLabel("��");
    
    private JRadioButton beHopRdo1 = null;
    private JRadioButton beHopRdo2 = null;
    private boolean beInHospitalFlag = true;
    
    private JComboBox beCompleteComboBox = new JComboBox(new String[]{"", "����", "������"});
    private JComboBox beSignComboBox = new JComboBox(new String[]{"", "ñ��", "��ñ��"});
    
    private OrDataVO orVo;
    
    private String[] emrPatTableColNames = new String[] {    		
        "chartNo",
    	"patName", 
    	"bedNo",            
    	"vsName", 
    	"admitDate",    		
    	"closeDate",     		
        "adminNoteFinish",
        "progressNoteFinish",
        "dischgNoteFinish",
        "totalFinish",
        "signStatus",
    	"encounterNo"};
    
    private String[] emrPatTableHeaders = new String[] {            
        "�f����",
    	"�f�w�m�W",
    	"�f�ɸ�",     	
    	"�D�v��v",  
    	"�J�|���",
    	"�X�|���",     		
        "�J�K",
        "���{",
        "�X�K",
        "�����_",
        "ñ���_", 
    	"�N��Ǹ�"};
    
    public NoteMainPanel getInstance() {
        return this;
    }
    
    public NoteMainPanel()
    {
    	this(null);
    }
    
    public NoteMainPanel(JFrame jFrame)
    {
        try
        {
            UIUtils.setNoteLookAndFeel();
            //Win7 1.5��Java�|��TimeZone���D
            TimeZone.setDefault(TimeZone.getTimeZone("Asia/Taipei"));
            
            this.sourceFrame = jFrame;
            
            //��l�ƨ��            
            jbInit();
                                            
            //this.reDoOpNoteEmrSheet();
                            
            //Win7 1.5��Java�|��TimeZone���D
            TimeZone.setDefault(TimeZone.getTimeZone("Asia/Taipei"));
        }
        catch (Exception e)
        {
            MessageManager.showException(e.getMessage(),e);
            logger.debug(e.getMessage(),e);
        }
    }
    
    
    
    private void jbInit() throws Exception
    { 
        //Create Image Dir
        ImageUtil imageUtil = new ImageUtil();
        imageUtil.checkHasImageDir();
        
        //�f�����g�e�����U,����i�H��mainPanel���ʧ@
        noteEkitPanel.setNoteMainPanel(this);
        
        //�]�wcss
        noteEkitPanel.getEkitPanel().getEkitCore().setDefaultStyleSheet();
        htmlViewer.getEkitPanel().getEkitCore().setDefaultStyleSheet();
    
        loadEmrIpdEncounterData(GlobalParameters.getInstance().getEncounterNo());        
        
        loadSecUserData();
        
        //NoteMainPanel(��ӭ����s�Ƴ]�w)
        {
            //�]�wNoteMainPanel�������t�m:�ϥ�BorderLayout�N�e������NORTH�BSOUTH�BWEST�BEAST�BCENTER���Ӱ϶�
        	this.setLayout(borderLayout);
        	
            //�bNORTH�϶��[�J�y���s�C�z
            {
                this.add(getToolBar(), BorderLayout.NORTH);
            }
            
            //�bCENTER�϶��[�JjPanel1
            {
                jPanel1.setLayout(formLayout1);
            	
                //��(2,2):TABS(jideTabbedPane)
                {
                    //�]�wjideTabbedPane���j�p(Default)
                    jideTabbedPane.setPreferredSize(new Dimension(210, 290));
                    
                    //TAB3(jSplitPane3):�f�w�M��
                    {
                        jSplitPane3.setOrientation(JSplitPane.VERTICAL_SPLIT);
                        jSplitPane3.setDividerLocation(160);
                                                
                        String columnSpecs = "70,0,80,0,30,0,115";
                        String rowSpecs = "25,3,25,3,25,3,25,3,25,3,25,3,25,3,25,3,25,3,25,3,25,3,25,3,25,3,25,3,25,3,25,3,25";
                        
                        jPanel4.setLayout(new FormLayout(columnSpecs, rowSpecs));
                        jPanel4.setBorder(BorderFactory.createTitledBorder("�d��"));
                        
                        jPanel4.add(new JLabel("��|�Ǹ�:"),	new CellConstraints(1, 1, 1, 1, CellConstraints.RIGHT, CellConstraints.DEFAULT));
                        jPanel4.add(encounterNoField, 		new CellConstraints(3, 1, 1, 1, CellConstraints.FILL, CellConstraints.DEFAULT));
                        
                        jPanel4.add(new JLabel("�f�����X:"),	new CellConstraints(1, 3, 1, 1, CellConstraints.RIGHT, CellConstraints.DEFAULT));
                        jPanel4.add(chartNoField, 			new CellConstraints(3, 3, 1, 1, CellConstraints.FILL, CellConstraints.DEFAULT));
                        
                        jPanel4.add(new JLabel("�m�W:"),		new CellConstraints(1, 5, 1, 1, CellConstraints.RIGHT, CellConstraints.DEFAULT));
                        jPanel4.add(patNameField, 			new CellConstraints(3, 5, 1, 1, CellConstraints.FILL, CellConstraints.DEFAULT));
                        
                        jPanel4.add(new JLabel("�ɦ�:"),		new CellConstraints(1, 7, 1, 1, CellConstraints.RIGHT, CellConstraints.DEFAULT));
                        jPanel4.add(bedNoField, 			new CellConstraints(3, 7, 1, 1, CellConstraints.FILL, CellConstraints.DEFAULT));
                                                                                                
                        btnQueryDept.setVisible(false);
                        jPanel4.add(new JLabel("��O:"),		new CellConstraints(1, 9, 1, 1, CellConstraints.RIGHT, CellConstraints.DEFAULT));
                        jPanel4.add(deptCodeField, 			new CellConstraints(3, 9, 1, 1, CellConstraints.FILL, CellConstraints.DEFAULT));
                        jPanel4.add(btnQueryDept,			new CellConstraints(5, 9, 1, 1, CellConstraints.FILL, CellConstraints.CENTER));
                        jPanel4.add(lblDeptName, 			new CellConstraints(7, 9, 1, 1, CellConstraints.FILL, CellConstraints.DEFAULT));                        
                        
                        btnQueryVs.setVisible(false);
                        jPanel4.add(new JLabel("�D�v��v:"),	new CellConstraints(1, 11, 1, 1, CellConstraints.RIGHT, CellConstraints.DEFAULT));
                        jPanel4.add(vsCodeField, 			new CellConstraints(3, 11, 1, 1, CellConstraints.FILL, CellConstraints.DEFAULT));
                        jPanel4.add(btnQueryVs,			    new CellConstraints(5, 11, 1, 1, CellConstraints.FILL, CellConstraints.CENTER));
                        jPanel4.add(lblVsName, 			    new CellConstraints(7, 11, 1, 1, CellConstraints.FILL, CellConstraints.DEFAULT));
                        
                        btnQueryStation.setVisible(false);
                        jPanel4.add(new JLabel("�@�z��:"), 	new CellConstraints(1, 13, 1, 1, CellConstraints.RIGHT, CellConstraints.DEFAULT));
                        jPanel4.add(branchCodeField, 		new CellConstraints(3, 13, 1, 1, CellConstraints.FILL, CellConstraints.DEFAULT));
                        jPanel4.add(btnQueryStation,		new CellConstraints(5, 13, 1, 1, CellConstraints.FILL, CellConstraints.CENTER));
                        jPanel4.add(lblStationName, 		new CellConstraints(7, 13, 1, 1, CellConstraints.FILL, CellConstraints.DEFAULT));
                        
                        jPanel4.add(new JLabel("��|���:"),	new CellConstraints(1, 15, 1, 1, CellConstraints.RIGHT, CellConstraints.DEFAULT));
                        jPanel4.add(admitDateField,			new CellConstraints(3, 15, 1, 1, CellConstraints.FILL, CellConstraints.DEFAULT));
                        jPanel4.add(dcb_admitDate,			new CellConstraints(5, 15, 1, 1, CellConstraints.FILL, CellConstraints.CENTER));
                        
                        jPanel4.add(new JLabel("��|�_:"), 	new CellConstraints(1, 17, 1, 1, CellConstraints.RIGHT, CellConstraints.DEFAULT));
                        beHopRdo1 = new JRadioButton("��|��");
                        beHopRdo2 = new JRadioButton("�w�X�|", true);
                        beInHospitalFlag = false;
                        
                        beHopRdo1.setBorderPainted(true);
                        beHopRdo2.setBorderPainted(true);
                        
                        ButtonGroup groupBeHopRdo = new ButtonGroup();
                        groupBeHopRdo.add(beHopRdo1);
                        groupBeHopRdo.add(beHopRdo2);
                        jPanel4.add(beHopRdo1,	            new CellConstraints(3, 17, 1, 1, CellConstraints.FILL, CellConstraints.DEFAULT));
                        jPanel4.add(beHopRdo2,	            new CellConstraints(3, 19, 1, 1, CellConstraints.FILL, CellConstraints.DEFAULT));
                        jPanel4.add(txtDischgDays,          new CellConstraints(5, 19, 1, 1, CellConstraints.FILL, CellConstraints.DEFAULT));
                        jPanel4.add(lblDischgDays,          new CellConstraints(7, 19, 1, 1, CellConstraints.FILL, CellConstraints.DEFAULT));
                        
                        jPanel4.add(new JLabel("�����_:"), 	new CellConstraints(1, 21, 1, 1, CellConstraints.RIGHT, CellConstraints.DEFAULT));
                        jPanel4.add(beCompleteComboBox,	    new CellConstraints(3, 21, 1, 1, CellConstraints.FILL, CellConstraints.DEFAULT));
                        beCompleteComboBox.setSelectedIndex(0);
                                                
                        jPanel4.add(new JLabel("ñ���_:"),         new CellConstraints(1, 23, 1, 1, CellConstraints.RIGHT, CellConstraints.DEFAULT));                        
                        jPanel4.add(beSignComboBox,     new CellConstraints(3, 23, 1, 1, CellConstraints.FILL, CellConstraints.DEFAULT));
                        beSignComboBox.setSelectedIndex(2);
                                                
                        jPanel4.add(queryButton,  new CellConstraints(7 ,23, 1, 1, CellConstraints.FILL, CellConstraints.DEFAULT));                                                                                                            
                                                
                        //update by leon 2015-07-24(��)
                        JLabel aLabel = new JLabel("����v������");                                                
                        jPanel4.add(aLabel ,  new CellConstraints(7 ,27, 1, 1, CellConstraints.FILL, CellConstraints.DEFAULT));                                            
                        jPanel4.add(queryButton1, new CellConstraints(7 ,29, 1, 1, CellConstraints.FILL, CellConstraints.DEFAULT));
                        jPanel4.add(queryButton2, new CellConstraints(7 ,31, 1, 1, CellConstraints.FILL, CellConstraints.DEFAULT));
                        jPanel4.add(queryButton3, new CellConstraints(7 ,33, 1, 1, CellConstraints.FILL, CellConstraints.DEFAULT));
                        
                        //queryButton1.setBackground(new Color(255,0,0));
                        //queryButton2.setBackground(new Color(255,255,0));
                        //queryButton3.setBackground(new Color(0,255,0));
                        //queryButton1.setForeground(new Color(255,0,0));
                        //queryButton2.setForeground(new Color(255,100,255));
                        //queryButton3.setForeground(new Color(0,0,255));                        
                        
                        if("VS".equalsIgnoreCase(GlobalParameters.getInstance().getDocType()))
                        {
                            aLabel.setVisible(true);
                            queryButton1.setVisible(true);
                            queryButton2.setVisible(true);
                            queryButton3.setVisible(true);
                            
                            if("28".equals(GlobalParameters.getInstance().getSecUserVO().getDeptCode()) || "2A".equals(GlobalParameters.getInstance().getSecUserVO().getDeptCode()))
                            {
                                aLabel.setVisible(false);                            
                                queryButton1.setVisible(false);
                                queryButton2.setVisible(false);                            
                                queryButton3.setVisible(false);
                            }
                        }
                        else
                        {
                            aLabel.setVisible(false);                            
                            queryButton1.setVisible(false);
                            queryButton2.setVisible(false);                            
                            queryButton3.setVisible(false);                            
                        }                        
                                                                       
                        jSplitPane3.add(jPanel4, JSplitPane.TOP);
                        
                        jideTabbedPane.addTab("�f�w�M��", jPanel4);
                        
                        if("OR".equals(GlobalParameters.getInstance().getSys()))
                        {
                            jideTabbedPane.setEnabledAt(0, false);
                        }
                    }
                    
                    //TAB1(jScrollPane1):�f�����
                    {
                        //update by leon 2012-06-07:�W�[�f�w��ư϶�
                        jSplitPane2.setOrientation(JSplitPane.VERTICAL_SPLIT);
                        jSplitPane2.setDividerLocation(350);                        
                        
                        jScrollPane1.getViewport().add(emrPatNoteTable, null);
                        
                        
                        
                        jSplitPane2.add(jScrollPane1, JSplitPane.BOTTOM);                        
                        
                        //�f�w���
                        //�]�w���󪩭��t�m
                        jPanel3.setLayout(new FormLayout("65,10,150","25,3,25,3,25,3,25,3,25,3,25,3,25,3,25,3,25,3,25,3,25"));
                        jPanel3.setBorder(BorderFactory.createTitledBorder("�f�w���"));                              
                        jPanel3.add(new JLabel("�N��Ǹ�:"), new CellConstraints(1, 1, 1, 1, CellConstraints.RIGHT, CellConstraints.DEFAULT));
                        jPanel3.add(txtEncounterNo, new CellConstraints(3, 1, 1, 1, CellConstraints.FILL, CellConstraints.DEFAULT));                        
                        jPanel3.add(new JLabel("�f����:"), new CellConstraints(1, 3, 1, 1, CellConstraints.RIGHT, CellConstraints.DEFAULT));
                        jPanel3.add(txtChartNo, new CellConstraints(3, 3, 1, 1, CellConstraints.FILL, CellConstraints.DEFAULT));                        
                        jPanel3.add(new JLabel("�m�W:"), new CellConstraints(1, 5, 1, 1, CellConstraints.RIGHT, CellConstraints.DEFAULT));
                        jPanel3.add(txtName, new CellConstraints(3, 5, 1, 1, CellConstraints.FILL, CellConstraints.DEFAULT)); 
                        jPanel3.add(new JLabel("�~��:"), new CellConstraints(1, 7, 1, 1, CellConstraints.RIGHT, CellConstraints.DEFAULT));
                        jPanel3.add(txtAge, new CellConstraints(3, 7, 1, 1, CellConstraints.FILL, CellConstraints.DEFAULT)); 
                        jPanel3.add(new JLabel("�ͤ�:"), new CellConstraints(1 ,9, 1, 1, CellConstraints.RIGHT, CellConstraints.DEFAULT));
                        jPanel3.add(txtBirthDate, new CellConstraints(3, 9, 1, 1, CellConstraints.FILL, CellConstraints.DEFAULT));
                        jPanel3.add(new JLabel("�ɦ�:"), new CellConstraints(1, 11, 1, 1, CellConstraints.RIGHT, CellConstraints.DEFAULT));
                        jPanel3.add(txtBedNo, new CellConstraints(3, 11, 1, 1, CellConstraints.FILL, CellConstraints.DEFAULT)); 
                        jPanel3.add(new JLabel("��O:"), new CellConstraints(1, 13, 1, 1, CellConstraints.RIGHT, CellConstraints.DEFAULT));
                        jPanel3.add(txtDeptName, new CellConstraints(3, 13, 1, 1, CellConstraints.FILL, CellConstraints.DEFAULT));
                        jPanel3.add(new JLabel("�D�v��v:"), new CellConstraints(1, 15, 1, 1, CellConstraints.RIGHT, CellConstraints.DEFAULT));
                        jPanel3.add(txtVsName, new CellConstraints(3, 15, 1, 1, CellConstraints.FILL, CellConstraints.DEFAULT));                                                                                              
                        jPanel3.add(new JLabel("�N����:"), new CellConstraints(1, 17, 1, 1, CellConstraints.RIGHT, CellConstraints.DEFAULT));
                        jPanel3.add(txtAdmitDate, new CellConstraints(3, 17, 1, 1, CellConstraints.FILL, CellConstraints.DEFAULT));                     
                        jPanel3.add(new JLabel("�ʧO:"), new CellConstraints(1, 19, 1, 1, CellConstraints.RIGHT, CellConstraints.DEFAULT));
                        jPanel3.add(txtSex, new CellConstraints(3, 19, 1, 1, CellConstraints.FILL, CellConstraints.DEFAULT));
                        jPanel3.add(lblBranchCode, new CellConstraints(1, 21, 1, 1, CellConstraints.RIGHT, CellConstraints.DEFAULT));
                        jPanel3.add(txtBranchCode, new CellConstraints(3, 21, 1, 1, CellConstraints.FILL, CellConstraints.DEFAULT));
                        
                        //�]�w����榡(�C��B����...��)
                        txtEncounterNo.setEditable(false);
                        txtChartNo.setEditable(false);
                        txtName.setEditable(false);
                        txtAge.setEditable(false);
                        txtBirthDate.setEditable(false);
                        txtBedNo.setEditable(false);
                        txtDeptName.setEditable(false);
                        txtVsName.setEditable(false);
                        txtAdmitDate.setEditable(false);
                        txtBranchCode.setEditable(false);
                        txtSex.setEditable(false);
                        
                        txtEncounterNo.setBorder(null);
                        txtChartNo.setBorder(null);
                        txtName.setBorder(null);
                        txtAge.setBorder(null);
                        txtBirthDate.setBorder(null);
                        txtBedNo.setBorder(null);
                        txtDeptName.setBorder(null);
                        txtVsName.setBorder(null);
                        txtAdmitDate.setBorder(null);
                        txtBranchCode.setBorder(null);
                        txtSex.setBorder(null);
                        
                        txtEncounterNo.setBackground(null);
                        txtChartNo.setBackground(null);
                        txtName.setBackground(null);
                        txtAge.setBackground(null);
                        txtBirthDate.setBackground(null);
                        txtBedNo.setBackground(null);
                        txtDeptName.setBackground(null);
                        txtVsName.setBackground(null);
                        txtAdmitDate.setBackground(null);
                        txtBranchCode.setBackground(null);
                        txtSex.setBackground(null);
                        
                        txtEncounterNo.setForeground(new Color(0,0,255));
                        txtChartNo.setForeground(new Color(0,0,255));
                        txtName.setForeground(new Color(0,0,255));
                        txtAge.setForeground(new Color(0,0,255));
                        txtBirthDate.setForeground(new Color(0,0,255));
                        txtBedNo.setForeground(new Color(0,0,255));
                        txtDeptName.setForeground(new Color(0,0,255));
                        txtVsName.setForeground(new Color(0,0,255));
                        txtAdmitDate.setForeground(new Color(0,0,255));
                        txtBranchCode.setForeground(new Color(0,0,255));
                        txtSex.setForeground(new Color(0,0,255));
                        
                        jSplitPane2.add(jPanel3, JSplitPane.TOP);
                        
                        jideTabbedPane.addTab("�f�����", jSplitPane2);
                    }
                        
                    //TAB2(jSplitPane1):���`����
                    {
                        jSplitPane1.setOrientation(JSplitPane.VERTICAL_SPLIT);
                        jSplitPane1.setDividerLocation(350);
                        
                        //�W��(���`)(jScrollPane3)
                        {
                            jList1.setFont(new Font(jList1.getFont().getFontName(), Font.BOLD, 14));
                            jScrollPane3.getViewport().add(jList1);
                            jScrollPane3.setBorder(BorderFactory.createTitledBorder("���`"));      
                            jScrollPane3.setPreferredSize(new Dimension(250,400));      
                            jSplitPane1.add(jScrollPane3, JSplitPane.TOP);
                        }
                        
                        //�U��(jideTabbedPane1)
                        {                        
                            jSplitPane1.add(jideTabbedPane1, JSplitPane.BOTTOM);
                        }
                        
                        jideTabbedPane.addTab("���`����", jSplitPane1);
                    }
                    
                    jPanel1.add(jideTabbedPane,
                    		new CellConstraints(2, 2, 1, 1, CellConstraints.FILL, CellConstraints.FILL));
                }
                
                //�k(4,2):jPanel2
                {   
                    //�]�wjPanel2�������t�m:�ϥ�CardLayout�N�e�������J�P�@�ˤ@�h�h���|�_��
                    jPanel2.setLayout(cardLayout); 
                    
                    //�Ĥ@�i�d(�f�ҥ���)(�����)
                    {
                        jPanel2.add("htmlViewer", htmlViewer);
                    }
                    
                    //�ĤG�i�d(���@�f�ҥ���)(�i�s��)
                    {
                    	double ad[][] = {
                        	//Columns
                            {                            	
                            	TableLayout.PREFERRED, //0 
                            	5,                     //1
                            	120,                   //2
                            	TableLayout.PREFERRED, //3                            	
                            	5,                     //4 
                            	70,                    //5        	
                            	TableLayout.PREFERRED, //6 
                            	5,                     //7        	
                            	80,                    //8                             	
                            	80,                   //9
                            	30,                    //10
                            	70                     //11
                            },
                            //Rows
                            {TableLayout.FILL}
                    	};
                    	
                        JPanel eventTimePanel = new JPanel(new TableLayout(ad));
                        //�Ĥ@��
                        eventTimePanel.add(eventTimeLabel,"0,0");
                        eventTimePanel.add(eventTimeTextField,"2,0");
                        eventTimePanel.add(dcb1,"3,0");
                        
                        //�ĤG��
                        eventTimePanel.add(lblTreNoteName,"5,0");
                        eventTimePanel.add(lbldtlKind,"5,0");                        
                                                
                        //�ĤT��
                        eventTimePanel.add(treNoteComboBox,"6,0");
                        eventTimePanel.add(wkSummaryComboBox,"6,0");  //���{�O���l����
                        eventTimePanel.add(dischgNoteComboBox,"6,0"); //�X�|�K�n�l����
                        
                        eventTimePanel.add(lblPersonInCharge,"8,0");
                        eventTimePanel.add(txtPersonInChargeName,"9,0"); //��ñ����v�m�W
                        eventTimePanel.add(btnPersonInCharge,"10,0"); //��ñ����v��ܫ��s
                        eventTimePanel.add(txtPersonInChargeCode,"11,0"); //��ñ����v�N�X
                        
                        txtPersonInChargeName.setEditable(false);
                        txtPersonInChargeCode.setEditable(false);
                        txtPersonInChargeCode.setVisible(false);                        
                        
                        
                        //eventTimePanel.add(new JTextField("2222333344445555"),"9,0");
                        
                        mPanel.setLayout(new BorderLayout());
                        mPanel.add(eventTimePanel, BorderLayout.NORTH);
                        
                        outsideNoteEkitPanel.setLayout(new BorderLayout());                        
                        outsideNoteEkitPanel.add(noteEkitPanel,BorderLayout.CENTER);
                        
                        //mPanel.add(noteEkitPanel,BorderLayout.CENTER);
                        mPanel.add(outsideNoteEkitPanel, BorderLayout.CENTER);
                        jPanel2.add("noteEkitPanel", mPanel);
                    }
                    
                    //�ĤT�i�d(�ť�)--->(�f�w�M��)
                    {
                        //jPanel2.add("blankPanel", jPanel5);                    	
                    	jScrollPane2.getViewport().add(emrPatListTable, null);
                    	
                    	jPanel5.setLayout(new BorderLayout());
                    	jPanel5.add(jScrollPane2, BorderLayout.CENTER);
                    	
                    	jPanel2.add("blankPanel", jPanel5);
                    }
                    
                    ((CardLayout)jPanel2.getLayout()).show(jPanel2, "htmlViewer");
                    
                    jPanel1.add(jPanel2,
                    		new CellConstraints(4, 2, 1, 1, CellConstraints.FILL, CellConstraints.FILL));
                }
                this.add(jPanel1, BorderLayout.CENTER);
            }                    
        }
        
        if("OR".equals(GlobalParameters.getInstance().getSys()) ||
        		GlobalParameters.getInstance().getLoginUserName().equals(
        				GlobalParameters.getInstance().getEmrIpdEncounterVO().getVsCode()))
        {
            noteTemplatePanel = new NoteTemplatePanel(GlobalParameters.getInstance().getLoginUserName());
            noteTemplateHosPanel = new NoteTemplatePanel(GlobalParameters.getInstance().getSecUserVO().getDeptCode());
            noteDataObjectPanel = new NoteDataObjectPanel();
            //�d���e���`�U,�i�H�����g�e���ʧ@
            noteTemplatePanel.setNoteEkitPanel(noteEkitPanel);
            noteTemplateHosPanel.setNoteEkitPanel(noteEkitPanel);
            noteDataObjectPanel.setNoteEkitPanel(noteEkitPanel);
            
            jideTabbedPane1.addTab("�ӤH�d��", noteTemplatePanel); //tab2-1
            jideTabbedPane1.addTab("��d��", noteTemplateHosPanel); //tab2-2
            jideTabbedPane1.addTab("�f�w���", noteDataObjectPanel); //tab2-3    
        }
        else
        {
            noteTemplatePanel = new NoteTemplatePanel(GlobalParameters.getInstance().getLoginUserName());
            noteTemplateVsPanel = new NoteTemplatePanel(GlobalParameters.getInstance().getEmrIpdEncounterVO().getVsCode());
            noteTemplateHosPanel = new NoteTemplatePanel(GlobalParameters.getInstance().getSecUserVO().getDeptCode());
            noteDataObjectPanel = new NoteDataObjectPanel();
            //�d���e���`�U,�i�H�����g�e���ʧ@
            noteTemplatePanel.setNoteEkitPanel(noteEkitPanel);
            noteTemplateVsPanel.setNoteEkitPanel(noteEkitPanel);
            noteTemplateHosPanel.setNoteEkitPanel(noteEkitPanel);
            noteDataObjectPanel.setNoteEkitPanel(noteEkitPanel);
        
            jideTabbedPane1.addTab("�ӤH�d��", noteTemplatePanel); //tab2-1
            jideTabbedPane1.addTab("VS�d��", noteTemplateVsPanel); //tab2-2
            jideTabbedPane1.addTab("��d��", noteTemplateHosPanel); //tab2-3
            jideTabbedPane1.addTab("�f�w���", noteDataObjectPanel); //tab2-4    
        }
        
        //�]�w�U��,�ƹ��ʧ@
        setAction();
        //�u��C���s�ʧ@
        setToolBarButtonAction();
        //Ū���ۭq�f�w�ܼƸ��,�ӤH�d�����,��d�����
        loadCategoryData(); 
        //��l�f�����g�����
        initNoteTextData();
        //�]�w�f�����
        setEventTimeTextValue();
        //�ھڨt�ΰѼƨӳ]�wUI���A
        setUIStatus();
        //binding ���Ҿ���
        setBinding();
        
        //�]�w���󤺮e
        if(GlobalParameters.getInstance().getEmrIpdEncounterVO() != null)
        {
            txtEncounterNo.setText(GlobalParameters.getInstance().getEmrIpdEncounterVO().getEncounterNo());
            txtChartNo.setText(GlobalParameters.getInstance().getEmrIpdEncounterVO().getChartNo());
            txtName.setText(GlobalParameters.getInstance().getEmrIpdEncounterVO().getPatName());
            txtAge.setText(GlobalParameters.getInstance().getEmrIpdEncounterVO().getAge());
            txtBirthDate.setText(GlobalParameters.getInstance().getEmrIpdEncounterVO().getBirthDate().toString());
            txtBedNo.setText(GlobalParameters.getInstance().getEmrIpdEncounterVO().getBedNo());
            txtDeptName.setText(GlobalParameters.getInstance().getEmrIpdEncounterVO().getDeptName());
            txtVsName.setText(GlobalParameters.getInstance().getEmrIpdEncounterVO().getVsName());
            txtAdmitDate.setText(GlobalParameters.getInstance().getEmrIpdEncounterVO().getAdmitDate().toString());
            txtBranchCode.setText(GlobalParameters.getInstance().getEmrIpdEncounterVO().getBranchCode());
            txtSex.setText(GlobalParameters.getInstance().getEmrIpdEncounterVO().getSexType().equals("F") ? "�k" : "�k");
        }
        else
        {
            orVo = parseOrData(currentVO.getSheetXmlMeta());
            txtEncounterNo.setText(orVo.encounterNo);
            txtChartNo.setText(orVo.chartNo);
            txtName.setText(orVo.vtPatName);
            txtAge.setText(orVo.vtAge);
            txtBirthDate.setText(orVo.vtPatBirth);
            txtBedNo.setText(orVo.vtBedNo);
            txtDeptName.setText(orVo.vtDeptCode);
            txtVsName.setText(orVo.vtDocCode);
            txtAdmitDate.setText(orVo.vtEncounterDate);
            txtSex.setText(orVo.vtPatSex.equals("F") ? "�k" : "�k");
            lblBranchCode.setText("");
            txtBranchCode.setText("");
        }            
    }
    
    public OrDataVO parseOrData(String xmlData)
    {
        OrDataVO orVO = new OrDataVO();
        if(xmlData == null)
        {
            return orVO;
        }
            
        try
        {
            org.dom4j.Document document = DocumentHelper.parseText(xmlData);    
            Element element = document.getRootElement();
            for(int i=0, size=element.nodeCount(); i<size; i++ )
            {
                Node node = element.node(i);
                if(node instanceof Element)
                {
                    Element cNode = (Element)node;
                    //System.out.println(cNode.getName());
                    if("basic".equals(cNode.getName()))
                    {
                        parseBasicData(cNode,orVO);
                    }                    
                }        
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return orVO;
    }
    
    public void parseBasicData(Element element,OrDataVO orVO) throws Exception
    {
        Class voClass = Class.forName("com.hcsaastech.ehis.doc.vo.OrDataVO");
        for( int i = 0, size = element.nodeCount(); i < size; i++ )
        {
            Node node = element.node(i);
            if(node instanceof Element)
            {
                Element cNode = (Element)node;
                Iterator it = cNode.attributes().iterator();
                while(it.hasNext())
                {
                    Attribute key = (Attribute)it.next();
                    //System.out.print("key = " + key.getName());
                    String value = cNode.attributeValue(key.getName());
                    //System.out.println("\tvalue = " + value);
          
                    try
                    {
                        Field field = voClass.getDeclaredField(key.getName());
                        field.set(orVO,value);
                    }
                    catch(NoSuchFieldException e)
                    {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
    }
    
    /**
     * �ץ��ƪ����s�ʧ@
     */
    public void fixDocumentText()
    {
        String documentBody = noteEkitPanel.getEkitPanel().getEkitCore().getDocumentBody();
        documentBody = StringUtil.fixPre(documentBody);
        documentBody = StringUtil.fixSpace("&#160;",documentBody);
        documentBody = StringUtil.fixSpace("&nbsp;",documentBody);
                    
        documentBody = StringUtil.fixPTag(documentBody);
            
        noteEkitPanel.getEkitPanel().getEkitCore().setDocumentText(documentBody);
    }
  
    /**�إߤu��C*/
    public JToolBar getToolBar()
    {
        JToolBar jtb = new JToolBar();
        jtb.setFloatable(false);            
        
        addBtn.setToolTipText("�s�W");
        delBtn.setToolTipText("�R��");
        tmpBtn.setToolTipText("�Ȧs");
        savBtn.setToolTipText("����");
        rbkBtn.setToolTipText("�_��");
        
        
        /*
        jtb.add(addBtn);
        jtb.add(new JToolBar.Separator());
        jtb.add(delBtn);
        jtb.add(new JToolBar.Separator());
        jtb.add(tmpBtn);
        jtb.add(new JToolBar.Separator());
        jtb.add(savBtn);
        jtb.add(new JToolBar.Separator());
        jtb.add(rbkBtn);
        jtb.add(new JToolBar.Separator());    
        jtb.add(getToolBarPanel());*/
        
        jtb.add(rbkBtn);
        jtb.add(new JToolBar.Separator());    
        jtb.add(addBtn);
        jtb.add(new JToolBar.Separator());
        jtb.add(delBtn);
        jtb.add(new JToolBar.Separator());
        jtb.add(tmpBtn);
        jtb.add(new JToolBar.Separator());
        //jtb.add(senBtn);
        //jtb.add(new JToolBar.Separator());
        //jtb.add(retBtn);
        //jtb.add(new JToolBar.Separator());
        jtb.add(savBtn);
        jtb.add(new JToolBar.Separator());
            
        jtb.add(getToolBarPanel());
        
        return jtb;
    }
  
    /**�إߤu��C���s�e��*/
    public JPanel getToolBarPanel()
    {
        JPanel panel;
        
        //printButton.setEnabled(true);
        //directDiagButton.setEnabled(true);        
                
        //�]�w���ƪ�        
        //��ڦ���m������줤�����j�@�Ӽe�׬�5�������
        //�Ĥ@�C8�ӫ��s
        //�ĤG�C2�ӫ��s        
        double ad[][] = {{                                       
            120,5,
            TableLayout.PREFERRED,5,
            TableLayout.PREFERRED,5,
            TableLayout.PREFERRED,5,                
            TableLayout.PREFERRED,5,
            TableLayout.PREFERRED,5, 
            TableLayout.PREFERRED,5, 
            TableLayout.PREFERRED,5, 
            TableLayout.PREFERRED                 
        },{TableLayout.FILL,TableLayout.FILL}};
        
        panel = new JPanel(new TableLayout(ad));                               
                
        panel.add(noteCategoryComboBox, "0,0");//�f�����O                
        panel.add(phraseButton, "2,0");        //���J���y
        panel.add(previewPrintButton, "4,0");  //�w���C�L            
        panel.add(defaultValueButton, "6,0");  //�w�]�d��
        panel.add(fourceEditButton, "8,0");    //�j��s��                                             
        panel.add(directDiagButton, "10,0");   //�E�_���@        
                
        panel.add(dittoButton,"0,1");        //DITTO        
        panel.add(diffPdfButton, "2,1");     //�������
        panel.add(printButton, "4,1");       //�ȥ��C�L        
        panel.add(hcaEmrButton, "6,1");      //�ɰeñ��        
        panel.add(progressAllButton, "8,1"); //���{�`ñ
        panel.add(checkChartButton, "10,1"); //�ʺ|�f��
        panel.add(pedigreeButton, "12,1");  //�a�ھ�        
        //panel.add(hcaEmrOpButton, "14,1"); //�ɰeñ��(��)
        
        hcaEmrOpButton.setVisible(false);
        pedigreeButton.setVisible(true);
        pedigreeButton.setEnabled(true);
        
        if("012004".equals(GlobalParameters.getInstance().getLoginUserName()))
        {            
            hcaEmrButton.setEnabled(true);      //�ɰeñ��
            printButton.setEnabled(true);       //�ȥ��C�L
            diffPdfButton.setEnabled(true);     //�������
            progressAllButton.setEnabled(true); //���{�`ñ
            hcaEmrOpButton.setEnabled(true);    //�ɰeñ��(��)
            pedigreeButton.setEnabled(true);    //�a�ھ�
        }
        else if("95A029".equals(GlobalParameters.getInstance().getLoginUserName()))
        {            
            hcaEmrButton.setEnabled(true);       //�ɰeñ��
            printButton.setEnabled(false);        //�ȥ��C�L
            diffPdfButton.setEnabled(true);     //�������
            progressAllButton.setEnabled(false); //���{�`ñ
            hcaEmrOpButton.setEnabled(false);    //�ɰeñ��(��)                    
        }        
        else
        {
            //���e�q�lñ��(����)
            //hcaEmrButton = new JButton("");
            //hcaEmrButton.setBackground(new Color(0,0,0,0));            
            //hcaEmrButton.setBorder(BorderFactory.createLineBorder(new Color(0,0,0,0),15));
            //hcaEmrButton.setRolloverEnabled(false);
            //panel.add(hcaEmrButton, "16,0");
                                                                
            /*
            hcaEmrButton.setEnabled(true);       //�ɰeñ��
            printButton.setEnabled(false);       //�ȥ��C�L
            diffPdfButton.setEnabled(true);     //�������
            progressAllButton.setEnabled(false); //���{�`ñ
            hcaEmrOpButton.setEnabled(false);    //�ɰeñ��(��)                        
            */
            
            hcaEmrButton.setEnabled(true);      //�ɰeñ��
            printButton.setEnabled(true);       //�ȥ��C�L
            diffPdfButton.setEnabled(true);     //�������
            progressAllButton.setEnabled(true); //���{�`ñ
            hcaEmrOpButton.setEnabled(true);    //�ɰeñ��(��)
            pedigreeButton.setEnabled(true);    //�a�ھ�
            
            /*
            if("2802".equals(GlobalParameters.getInstance().getLoginUserName()))
            {
                pedigreeButton.setVisible(true);
            }
            */
        }
    
        return panel;
    }

    /**
     * �ھڨt�ΰѼƨӳ]�wUI���A
     */
    public void setUIStatus()
    {
        //�Y����N�t�εn�J�A�����\�s�W�ΧR���A�ȯ�ק��N���e
        if(GlobalParameters.getInstance().getSys() != null &&
        		GlobalParameters.getInstance().getSys().equals("OR"))
        {                    
            addBtn.setEnabled(false);
            delBtn.setEnabled(false);
            //�v���B�m�ﶵ
            lblTreNoteName.setVisible(false);
            treNoteComboBox.setVisible(false);
        }
    }
    
    /**
     * binding ���Ҿ���
     */
    private void setBinding(){
        if ("TreNote".equals(filterString)) {
            String[] props = new String[] {"reportDate", "examOrderDesc"};
            Object[] components = new Object[] {eventTimeTextField, treNoteComboBox};
            binding.setBinding(props, components);
        } else {
            String[] props = new String[] {"reportDate"};
            Object[] components = new Object[] {eventTimeTextField};
            binding.setBinding(props, components);
        }
    }
    
    /**
     * Ū���f�����O(�U�ԧ�[�J�K�B�X�K....])���(From Table emr_note_category)
     */
    public void loadCategoryData() throws Exception
    {
        try
        {
            //mark by leon 2012-05-18:�̴������ܭק�DB �����N�e�����檺����
            String sql = "select * from emr_note_category where use_kind = 'Swing' ";
            
            //�Y����N�t�εn�J�A�U�Կ��w�]�u����N�i�H���
            if(GlobalParameters.getInstance().getSys()!= null &&
            		GlobalParameters.getInstance().getSys().equals("OR"))
            {
                sql += "and note_type = '" + GlobalParameters.OR_NOTE + "' ";
            }
            sql += "order by id";
            
            //�d�ߨñN���G�m�J�U�Կ���@�ﶵ
            ArrayList emrNoteCategoryData = EmrNoteCategoryDAO.getInstance().findAllBySql(sql);
            HCComboBoxModel hcbm = new HCComboBoxModel(emrNoteCategoryData, "typeDesc",
            		"com.hcsaastech.ehis.doc.vo.EmrNoteCategoryVO");
            noteCategoryComboBox.setModel(hcbm);
            
            //�w�]��ܬ�ProgressNote
            EmrNoteCategoryVO defaultCategoryVO = null;
            for(int i=0; i<emrNoteCategoryData.size(); i++)
            {
                defaultCategoryVO = (EmrNoteCategoryVO) emrNoteCategoryData.get(i);
                //�w�]����G���{�O��[ProgressNote]
                if("ProgressNote".equals(defaultCategoryVO.getNoteType()))                
                {
                    noteCategoryComboBox.setSelectedIndex(i);
                    break;
                }
            }
            
            //�Y�d�ߵ��G���šA�w�]��ܲĤ@��
            if(defaultCategoryVO == null)
            {
                defaultCategoryVO = (EmrNoteCategoryVO) emrNoteCategoryData.get(0);
            }
            
            //�Y�d�ߵ��G������
            if(emrNoteCategoryData.size() > 0)
            {
                //�̹w�]�����O�h�d�ߩҹ��������`�M��
                loadChapterData(defaultCategoryVO.getId());
                //�ñN�ҿ�ܪ����O�x�s������ܼ�filterString��
                this.filterString = defaultCategoryVO.getNoteType();
                //�̩ҿ�ܪ����O�d�߯f�����
                loadPatNoteData();
                //�̯f�����OIDŪ���ۭq�f�w�ܼƸ��
                noteDataObjectPanel.loadDataobjectData(defaultCategoryVO.getId().toString(),((EmrNoteChapterVO)emrNoteChapterData.get(0)).getNoteChapter());
                noteDataObjectPanel.setNoteCategory(filterString);
                //�̯f�����OIDŪ���ӤH�d�����
                noteTemplatePanel.loadTemplatemstData(defaultCategoryVO.getNoteType());
                //update by leon 2012-06-04:�̯f�����OIDŪ��VS�d�����
                if(noteTemplateVsPanel != null){
                    noteTemplateVsPanel.loadTemplatemstData(defaultCategoryVO.getNoteType());
                }
                //�̯f�����OIDŪ����d�����
                noteTemplateHosPanel.loadTemplatemstData(defaultCategoryVO.getNoteType());                
            }
        }
        catch(Exception e)
        {
            MessageManager.showException(e.getMessage(),e);
            logger.debug(e.getMessage(),e);
        }
    }

    /**Ū�����`���*/
    public void loadChapterData(Long id)
    {
        try
        {
            String sql = "";            
            sql = sql + " SELECT ";
            sql = sql + "   * ";
            sql = sql + " FROM ";
            sql = sql + "   emr_note_chapter ";
            sql = sql + " WHERE ";
            sql = sql + "   emr_note_category_id = '"+ id +"' ";
            sql = sql + " ORDER BY ";
            sql = sql + "   seq asc ";
            
            emrNoteChapterData = EmrNoteChapterDAO.getInstance().findAllBySql(sql);
        }
        catch (Exception ex)
        {
            MessageManager.showException(ex.getMessage(), ex);
            logger.debug(ex.getMessage(), ex);
        }
    }
    
    public void loadEmrPatList1()
    {
        try
        {
            loadEmrPatListF = "loadEmrPatList1";
            
            Connection conn; 
            Statement stmt = null;
            ResultSet rs = null;
            
            String sql = "";
            StringBuffer sql_1 = new StringBuffer();            
                        
            try
            {            
                conn = DBConnectionHelper.getInstance().getMySqlConnection();
                stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                                                        
                sql_1.append(" SELECT DISTINCT a.caseno ");
                sql_1.append("  FROM adt.adt_tran_bed a ");                
                sql_1.append(" WHERE a.st_date > to_date('2015-07-16', 'yyyy-mm-dd') ");                
                sql_1.append("   AND a.dept_code != a.old_dept_code ");                
                sql_1.append("   AND trunc(SYSDATE) - trunc(a.st_date) > 5 ");                
                sql_1.append("   AND trunc(SYSDATE) - trunc(a.st_date) <= 62 ");
                sql_1.append("   AND a.old_vs_code = '"+ GlobalParameters.getInstance().getLoginUserName() +"' ");
                sql = sql_1.toString();
                                                                                        
                rs = stmt.executeQuery(sql.toString()); 
                CallableStatement stproc_stmt = conn.prepareCall("{call P_CHECK_MEDICAL_CHART(?,?)}");
                while(rs.next())
                {
                    stproc_stmt.registerOutParameter(2, OracleTypes.VARCHAR);
                    stproc_stmt.setString(1, rs.getString("caseno"));
                    stproc_stmt.execute();
                }
                rs.close();
                stproc_stmt.close();
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(this, "loadEmrPatList1 Exception: " + e.toString());
            }
            finally
            {
                DBConnectionHelper.getInstance().cleanRsAndStmt(rs,stmt);           
            }                    
            
            sql_1 = new StringBuffer();
            sql_1.append(" SELECT DISTINCT a.caseno ");
            sql_1.append("  FROM adt.adt_tran_bed        a ");
            sql_1.append("	 ,emr.emr_pat_note_errlog b ");
            sql_1.append(" WHERE a.st_date > to_date('2015-07-16', 'yyyy-mm-dd') ");
            sql_1.append("   AND a.dept_code != a.old_dept_code ");
            sql_1.append("   AND trunc(SYSDATE) - trunc(a.st_date) > 5 ");
            sql_1.append("   AND trunc(SYSDATE) - trunc(a.st_date) <= 62 ");
            sql_1.append("   AND a.old_vs_code = '"+ GlobalParameters.getInstance().getLoginUserName() +"' ");
            sql_1.append("   AND a.caseno = b.encounter_no ");
            sql_1.append("   AND b.person_in_charge = '"+ GlobalParameters.getInstance().getLoginUserName() +"' ");
            sql_1.append("   AND trunc(b.report_date) <= trunc(a.st_date) ");
            sql_1.append("   AND b.report_third_type_scid != 'D' ");
                                                    
            //�`�駹���_
            String strTotalFinish = " SELECT decode(COUNT(1),0,'Y','N') FROM emr.emr_pat_note_errlog WHERE emr_pat_note_errlog.encounter_no= en.encounter_no AND emr_pat_note_errlog.person_in_charge = '"+ GlobalParameters.getInstance().getLoginUserName() +"' ";
            //�J�K�����_
            String strAdminNoteFinish = " SELECT decode(COUNT(1),0,'Y','N') FROM emr.emr_pat_note_errlog WHERE emr_pat_note_errlog.encounter_no= en.encounter_no AND emr_pat_note_errlog.report_subtype_scid = 'adminnote' AND emr_pat_note_errlog.person_in_charge = '"+ GlobalParameters.getInstance().getLoginUserName() +"' ";
            //���{�����_
            String strProgressNoteFinish = " SELECT decode(COUNT(1),0,'Y','N') FROM emr.emr_pat_note_errlog WHERE emr_pat_note_errlog.encounter_no= en.encounter_no AND emr_pat_note_errlog.report_subtype_scid = 'progressnote' AND emr_pat_note_errlog.person_in_charge = '"+ GlobalParameters.getInstance().getLoginUserName() +"' ";
            //�X�K�����_
            String strDischgNoteFinish = " SELECT decode(COUNT(1),0,'Y','N') FROM emr.emr_pat_note_errlog WHERE emr_pat_note_errlog.encounter_no= en.encounter_no AND emr_pat_note_errlog.report_subtype_scid = 'dischgnote' AND emr_pat_note_errlog.person_in_charge = '"+ GlobalParameters.getInstance().getLoginUserName() +"' ";                                    
            //ñ���_
            String strSignStatus = " SELECT decode(COUNT(1),0,'N','Y') FROM emr.emr_diag_confirmlog WHERE emr_diag_confirmlog.encounter_no =en.encounter_no AND emr_diag_confirmlog.return_by IS NULL ";
            
            StringBuffer emrIpdEncounterSQL = new StringBuffer();                     
            emrIpdEncounterSQL.append(" SELECT * FROM (");                         
            //query_type:01:�D�v��v���ۤv���M��
            emrIpdEncounterSQL.append(" SELECT ");
            emrIpdEncounterSQL.append("     '01' AS query_type ");
            emrIpdEncounterSQL.append("     ,("+ strTotalFinish +")AS totalFinish ");
            emrIpdEncounterSQL.append("     ,("+ strAdminNoteFinish +")AS adminNoteFinish ");
            emrIpdEncounterSQL.append("     ,("+ strProgressNoteFinish +")AS progressNoteFinish ");
            emrIpdEncounterSQL.append("     ,("+ strDischgNoteFinish +")AS dischgNoteFinish ");            
            emrIpdEncounterSQL.append("     ,en.* ");
            emrIpdEncounterSQL.append("     ,("+ strSignStatus +")AS signStatus ");
            emrIpdEncounterSQL.append(" FROM ");
            emrIpdEncounterSQL.append("     emr.emr_ipd_encounter en ");
            emrIpdEncounterSQL.append(" WHERE ");
            emrIpdEncounterSQL.append("     en.encounter_no IN("+ sql_1.toString() +") ");                                                                                                                                                            
            emrIpdEncounterSQL.append(" )s WHERE 1=1 ");
            
            if("VS".equalsIgnoreCase(GlobalParameters.getInstance().getDocType()))
            {
                emrIpdEncounterSQL.append(" ORDER BY s.query_type, s.vs_code, s.close_date, s.bed_no ");    
            }
            else
            {
                emrIpdEncounterSQL.append(" ORDER BY s.query_type, s.vs_code, s.bed_no ");
            }            
                        
            ArrayList list = EmrIpdEncounterDAO.getInstance().findAllBySql(emrIpdEncounterSQL.toString());
            emrPatListTable.setModel(new HCTableModel(list, emrPatTableColNames, emrPatTableHeaders, "com.hcsaastech.ehis.doc.vo.EmrIpdEncounterVO", null));
            for(int i=0; i<list.size(); i++)
            {
                EmrIpdEncounterVO vo = (EmrIpdEncounterVO) list.get(i);
                
                if(vo.getEncounterNo().equals(GlobalParameters.getInstance().getEncounterNo()))
                {
                    //����Focus��Rows
                    emrPatListTable.setRowSelectionInterval(i, i);
                    //����Focus��Columns
                    //emrPatListTable.setColumnSelectionInterval(0, 3);
                    emrPatListTable.setColumnSelectionInterval(0, emrPatTableColNames.length-1);
                    break;
                }
            }
            
            //20131129 Jeff �h������:�W�[�d�ߵ��G���A�[�e�J�|������L�k�q�X������            
            emrPatListTable.setAutoResizeMode(0);
            emrPatListTable.setFont(new Font(emrPatListTable.getFont().getFontName(), Font.PLAIN, 16));
            emrPatListTable.setRowHeight(25);
                       
            emrPatListTable.getColumnModel().getColumn(0).setPreferredWidth(70);  //�f����chartNo
            emrPatListTable.getColumnModel().getColumn(1).setPreferredWidth(120);  //�f�w�m�WpatName
            emrPatListTable.getColumnModel().getColumn(2).setPreferredWidth(80);  //�ɸ�bedNo            
            emrPatListTable.getColumnModel().getColumn(3).setPreferredWidth(90);  //�D�v��vvsName            
            emrPatListTable.getColumnModel().getColumn(4).setPreferredWidth(90);  //�J�|���admitDate            
            emrPatListTable.getColumnModel().getColumn(5).setPreferredWidth(90);  //�X�|���closeDate            
            emrPatListTable.getColumnModel().getColumn(6).setPreferredWidth(50);  //�J�KadminNoteFinish
            emrPatListTable.getColumnModel().getColumn(7).setPreferredWidth(50);  //���{progressNoteFinish
            emrPatListTable.getColumnModel().getColumn(8).setPreferredWidth(50);  //�X�KdischgNoteFinish   
            emrPatListTable.getColumnModel().getColumn(9).setPreferredWidth(50);  //�����_totalFinish
            emrPatListTable.getColumnModel().getColumn(10).setPreferredWidth(50); //ñ��signStatus
            emrPatListTable.getColumnModel().getColumn(11).setPreferredWidth(110);//��|�Ǹ�encounterNo
                                                              
            DefaultTableCellRenderer cdr = (DefaultTableCellRenderer)emrPatListTable.getDefaultRenderer(Integer.class);
            cdr.setHorizontalAlignment(SwingConstants.LEFT);
            cdr.setVerticalAlignment(SwingConstants.CENTER);                    
            
            emrPatListTable.getColumnModel().getColumn(0).setCellRenderer(cdr);
            emrPatListTable.getColumnModel().getColumn(1).setCellRenderer(cdr);
            emrPatListTable.getColumnModel().getColumn(2).setCellRenderer(cdr);
            emrPatListTable.getColumnModel().getColumn(3).setCellRenderer(cdr);
            emrPatListTable.getColumnModel().getColumn(4).setCellRenderer(cdr);
            emrPatListTable.getColumnModel().getColumn(5).setCellRenderer(cdr);
            emrPatListTable.getColumnModel().getColumn(6).setCellRenderer(cdr);
            emrPatListTable.getColumnModel().getColumn(7).setCellRenderer(cdr);
            emrPatListTable.getColumnModel().getColumn(8).setCellRenderer(cdr);
            emrPatListTable.getColumnModel().getColumn(9).setCellRenderer(cdr);
            emrPatListTable.getColumnModel().getColumn(10).setCellRenderer(cdr);
            emrPatListTable.getColumnModel().getColumn(11).setCellRenderer(cdr);
                                              
            //Win7 1.5��Java�|��TimeZone���D
            //TimeZone.setDefault(TimeZone.getTimeZone("Asia/Taipei"));            
        }
        catch (Exception ex)
        {
            MessageManager.showException(ex.getMessage(), ex);
            logger.debug(ex.getMessage(), ex);
        }
    }
    
    public void loadEmrPatList2()
    {
        try
        {
            loadEmrPatListF = "loadEmrPatList2";
            Connection conn; 
            Statement stmt = null;
            ResultSet rs = null;
            
            String sql = "";
            StringBuffer sql_1 = new StringBuffer();
            StringBuffer sql_2 = new StringBuffer();
            
            try
            {            
                conn = DBConnectionHelper.getInstance().getMySqlConnection();
                stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                                                        
                sql_1.append(" SELECT DISTINCT a.caseno ");
                sql_1.append("  FROM adt.adt_tran_bed a ");                
                sql_1.append(" WHERE a.st_date > to_date('2015-07-16', 'yyyy-mm-dd') ");                
                sql_1.append("   AND a.dept_code != a.old_dept_code ");                
                sql_1.append("   AND trunc(SYSDATE) - trunc(a.st_date) > 3 ");                
                sql_1.append("   AND trunc(SYSDATE) - trunc(a.st_date) <= 5 ");
                sql_1.append("   AND a.old_vs_code = '"+ GlobalParameters.getInstance().getLoginUserName() +"' ");
                sql = sql_1.toString();
                                                                                        
                rs = stmt.executeQuery(sql.toString()); 
                CallableStatement stproc_stmt = conn.prepareCall("{call P_CHECK_MEDICAL_CHART(?,?)}");
                while(rs.next())
                {
                    stproc_stmt.registerOutParameter(2, OracleTypes.VARCHAR);
                    stproc_stmt.setString(1, rs.getString("caseno"));
                    stproc_stmt.execute();
                }
                rs.close();
                stproc_stmt.close();
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(this, "loadEmrPatList2 Exception: " + e.toString());
            }
            finally
            {
                DBConnectionHelper.getInstance().cleanRsAndStmt(rs,stmt);           
            }
            
            sql_1 = new StringBuffer();
            sql_1.append(" SELECT DISTINCT a.caseno ");
            sql_1.append("  FROM adt.adt_tran_bed        a ");
            sql_1.append("        ,emr.emr_pat_note_errlog b ");
            sql_1.append(" WHERE a.st_date > to_date('2015-07-16', 'yyyy-mm-dd') ");
            sql_1.append("   AND a.dept_code != a.old_dept_code ");
            sql_1.append("   AND trunc(SYSDATE) - trunc(a.st_date) > 3 ");
            sql_1.append("   AND trunc(SYSDATE) - trunc(a.st_date) <= 5 ");
            sql_1.append("   AND a.old_vs_code = '"+ GlobalParameters.getInstance().getLoginUserName() +"' ");
            sql_1.append("   AND a.caseno = b.encounter_no ");
            sql_1.append("   AND b.person_in_charge = '"+ GlobalParameters.getInstance().getLoginUserName() +"' ");
            sql_1.append("   AND trunc(b.report_date) <= trunc(a.st_date) ");
            sql_1.append("   AND b.report_third_type_scid != 'D' ");
                                                    
             //�`�駹���_
             String strTotalFinish = " SELECT decode(COUNT(1),0,'Y','N') FROM emr.emr_pat_note_errlog WHERE emr_pat_note_errlog.encounter_no= en.encounter_no AND emr_pat_note_errlog.person_in_charge = '"+ GlobalParameters.getInstance().getLoginUserName() +"' ";
             //�J�K�����_
             String strAdminNoteFinish = " SELECT decode(COUNT(1),0,'Y','N') FROM emr.emr_pat_note_errlog WHERE emr_pat_note_errlog.encounter_no= en.encounter_no AND emr_pat_note_errlog.report_subtype_scid = 'adminnote' AND emr_pat_note_errlog.person_in_charge = '"+ GlobalParameters.getInstance().getLoginUserName() +"' ";
             //���{�����_
             String strProgressNoteFinish = " SELECT decode(COUNT(1),0,'Y','N') FROM emr.emr_pat_note_errlog WHERE emr_pat_note_errlog.encounter_no= en.encounter_no AND emr_pat_note_errlog.report_subtype_scid = 'progressnote' AND emr_pat_note_errlog.person_in_charge = '"+ GlobalParameters.getInstance().getLoginUserName() +"' ";
             //�X�K�����_
             String strDischgNoteFinish = " SELECT decode(COUNT(1),0,'Y','N') FROM emr.emr_pat_note_errlog WHERE emr_pat_note_errlog.encounter_no= en.encounter_no AND emr_pat_note_errlog.report_subtype_scid = 'dischgnote' AND emr_pat_note_errlog.person_in_charge = '"+ GlobalParameters.getInstance().getLoginUserName() +"' ";                                    
             //ñ���_
             String strSignStatus = " SELECT decode(COUNT(1),0,'N','Y') FROM emr.emr_diag_confirmlog WHERE emr_diag_confirmlog.encounter_no =en.encounter_no AND emr_diag_confirmlog.return_by IS NULL ";
            
            StringBuffer emrIpdEncounterSQL = new StringBuffer();                     
            emrIpdEncounterSQL.append(" SELECT * FROM (");                         
            //query_type:01:�D�v��v���ۤv���M��
            emrIpdEncounterSQL.append(" SELECT ");
            emrIpdEncounterSQL.append("     '01' AS query_type ");
            emrIpdEncounterSQL.append("     ,("+ strTotalFinish +")AS totalFinish ");
            emrIpdEncounterSQL.append("     ,("+ strAdminNoteFinish +")AS adminNoteFinish ");
            emrIpdEncounterSQL.append("     ,("+ strProgressNoteFinish +")AS progressNoteFinish ");
            emrIpdEncounterSQL.append("     ,("+ strDischgNoteFinish +")AS dischgNoteFinish ");            
            emrIpdEncounterSQL.append("     ,en.* ");
            emrIpdEncounterSQL.append("     ,("+ strSignStatus +")AS signStatus ");
            emrIpdEncounterSQL.append(" FROM ");
            emrIpdEncounterSQL.append("     emr.emr_ipd_encounter en ");
            emrIpdEncounterSQL.append(" WHERE ");
            emrIpdEncounterSQL.append("     en.encounter_no IN("+ sql_1.toString() +") ");                                                                                                                                                            
            emrIpdEncounterSQL.append(" )s WHERE 1=1 ");
            
                if("VS".equalsIgnoreCase(GlobalParameters.getInstance().getDocType()))
                {
                    emrIpdEncounterSQL.append(" ORDER BY s.query_type, s.vs_code, s.close_date, s.bed_no ");    
                }
                else
                {
                    emrIpdEncounterSQL.append(" ORDER BY s.query_type, s.vs_code, s.bed_no ");
                } 
                        
            ArrayList list = EmrIpdEncounterDAO.getInstance().findAllBySql(emrIpdEncounterSQL.toString());
            emrPatListTable.setModel(new HCTableModel(list, emrPatTableColNames, emrPatTableHeaders, "com.hcsaastech.ehis.doc.vo.EmrIpdEncounterVO", null));
            for(int i=0; i<list.size(); i++)
            {
                EmrIpdEncounterVO vo = (EmrIpdEncounterVO) list.get(i);
                
                if(vo.getEncounterNo().equals(GlobalParameters.getInstance().getEncounterNo()))
                {
                    //����Focus��Rows
                    emrPatListTable.setRowSelectionInterval(i, i);
                    //����Focus��Columns
                    //emrPatListTable.setColumnSelectionInterval(0, 3);
                    emrPatListTable.setColumnSelectionInterval(0, emrPatTableColNames.length-1);
                    break;
                }
            }
            
            //20131129 Jeff �h������:�W�[�d�ߵ��G���A�[�e�J�|������L�k�q�X������            
            emrPatListTable.setAutoResizeMode(0);                        
            emrPatListTable.setFont(new Font(emrPatListTable.getFont().getFontName(), Font.PLAIN, 16));
            emrPatListTable.setRowHeight(25);
                       
            emrPatListTable.getColumnModel().getColumn(0).setPreferredWidth(70);  //�f����chartNo
            emrPatListTable.getColumnModel().getColumn(1).setPreferredWidth(120);  //�f�w�m�WpatName
            emrPatListTable.getColumnModel().getColumn(2).setPreferredWidth(80);  //�ɸ�bedNo            
            emrPatListTable.getColumnModel().getColumn(3).setPreferredWidth(90);  //�D�v��vvsName            
            emrPatListTable.getColumnModel().getColumn(4).setPreferredWidth(90);  //�J�|���admitDate            
            emrPatListTable.getColumnModel().getColumn(5).setPreferredWidth(90);  //�X�|���closeDate            
            emrPatListTable.getColumnModel().getColumn(6).setPreferredWidth(50);  //�J�KadminNoteFinish
            emrPatListTable.getColumnModel().getColumn(7).setPreferredWidth(50);  //���{progressNoteFinish
            emrPatListTable.getColumnModel().getColumn(8).setPreferredWidth(50);  //�X�KdischgNoteFinish   
            emrPatListTable.getColumnModel().getColumn(9).setPreferredWidth(50);  //�����_totalFinish
            emrPatListTable.getColumnModel().getColumn(10).setPreferredWidth(50); //ñ��signStatus
            emrPatListTable.getColumnModel().getColumn(11).setPreferredWidth(110);//��|�Ǹ�encounterNo
                                                              
            DefaultTableCellRenderer cdr = (DefaultTableCellRenderer)emrPatListTable.getDefaultRenderer(Integer.class);
            cdr.setHorizontalAlignment(SwingConstants.LEFT);
            cdr.setVerticalAlignment(SwingConstants.CENTER);                    
            
            emrPatListTable.getColumnModel().getColumn(0).setCellRenderer(cdr);
            emrPatListTable.getColumnModel().getColumn(1).setCellRenderer(cdr);
            emrPatListTable.getColumnModel().getColumn(2).setCellRenderer(cdr);
            emrPatListTable.getColumnModel().getColumn(3).setCellRenderer(cdr);
            emrPatListTable.getColumnModel().getColumn(4).setCellRenderer(cdr);
            emrPatListTable.getColumnModel().getColumn(5).setCellRenderer(cdr);
            emrPatListTable.getColumnModel().getColumn(6).setCellRenderer(cdr);
            emrPatListTable.getColumnModel().getColumn(7).setCellRenderer(cdr);
            emrPatListTable.getColumnModel().getColumn(8).setCellRenderer(cdr);
            emrPatListTable.getColumnModel().getColumn(9).setCellRenderer(cdr);
            emrPatListTable.getColumnModel().getColumn(10).setCellRenderer(cdr);
            emrPatListTable.getColumnModel().getColumn(11).setCellRenderer(cdr);
                                              
            //Win7 1.5��Java�|��TimeZone���D
            //TimeZone.setDefault(TimeZone.getTimeZone("Asia/Taipei"));            
        }
        catch (Exception ex)
        {
            MessageManager.showException(ex.getMessage(), ex);
            logger.debug(ex.getMessage(), ex);
        }
    }
    
    public void loadEmrPatList3()
    {
        try
        {
            loadEmrPatListF = "loadEmrPatList3";
            Connection conn; 
            Statement stmt = null;
            ResultSet rs = null;
            
            String sql = "";
            StringBuffer sql_1 = new StringBuffer();
            StringBuffer sql_2 = new StringBuffer();
            
            try
            {            
                conn = DBConnectionHelper.getInstance().getMySqlConnection();
                stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                                                        
                sql_1.append(" SELECT DISTINCT a.caseno ");
                sql_1.append("  FROM adt.adt_tran_bed a ");                
                sql_1.append(" WHERE a.st_date > to_date('2015-07-16', 'yyyy-mm-dd') ");                
                sql_1.append("   AND a.dept_code != a.old_dept_code ");                
                sql_1.append("   AND trunc(SYSDATE) - trunc(a.st_date) > 0 ");                
                sql_1.append("   AND trunc(SYSDATE) - trunc(a.st_date) <= 3 ");
                sql_1.append("   AND a.old_vs_code = '"+ GlobalParameters.getInstance().getLoginUserName() +"' ");
                sql = sql_1.toString();
                                                                                        
                rs = stmt.executeQuery(sql.toString()); 
                CallableStatement stproc_stmt = conn.prepareCall("{call P_CHECK_MEDICAL_CHART(?,?)}");
                while(rs.next())
                {
                    stproc_stmt.registerOutParameter(2, OracleTypes.VARCHAR);
                    stproc_stmt.setString(1, rs.getString("caseno"));
                    stproc_stmt.execute();
                }
                rs.close();
                stproc_stmt.close();
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(this, "loadEmrPatList3 Exception: " + e.toString());
            }
            finally
            {
                DBConnectionHelper.getInstance().cleanRsAndStmt(rs,stmt);           
            }
            
            sql_1 = new StringBuffer();
            sql_1.append(" SELECT DISTINCT a.caseno ");
            sql_1.append("  FROM adt.adt_tran_bed        a ");
            sql_1.append("        ,emr.emr_pat_note_errlog b ");
            sql_1.append(" WHERE a.st_date > to_date('2015-07-16', 'yyyy-mm-dd') ");
            sql_1.append("   AND a.dept_code != a.old_dept_code ");
            sql_1.append("   AND trunc(SYSDATE) - trunc(a.st_date) > 0 ");
            sql_1.append("   AND trunc(SYSDATE) - trunc(a.st_date) <= 3 ");
            sql_1.append("   AND a.old_vs_code = '"+ GlobalParameters.getInstance().getLoginUserName() +"' ");
            sql_1.append("   AND a.caseno = b.encounter_no ");
            sql_1.append("   AND b.person_in_charge = '"+ GlobalParameters.getInstance().getLoginUserName() +"' ");
            sql_1.append("   AND trunc(b.report_date) <= trunc(a.st_date) ");
            sql_1.append("   AND b.report_third_type_scid != 'D' ");
                                                    
             //�`�駹���_
             String strTotalFinish = " SELECT decode(COUNT(1),0,'Y','N') FROM emr.emr_pat_note_errlog WHERE emr_pat_note_errlog.encounter_no= en.encounter_no AND emr_pat_note_errlog.person_in_charge = '"+ GlobalParameters.getInstance().getLoginUserName() +"' ";
             //�J�K�����_
             String strAdminNoteFinish = " SELECT decode(COUNT(1),0,'Y','N') FROM emr.emr_pat_note_errlog WHERE emr_pat_note_errlog.encounter_no= en.encounter_no AND emr_pat_note_errlog.report_subtype_scid = 'adminnote' AND emr_pat_note_errlog.person_in_charge = '"+ GlobalParameters.getInstance().getLoginUserName() +"' ";
             //���{�����_
             String strProgressNoteFinish = " SELECT decode(COUNT(1),0,'Y','N') FROM emr.emr_pat_note_errlog WHERE emr_pat_note_errlog.encounter_no= en.encounter_no AND emr_pat_note_errlog.report_subtype_scid = 'progressnote' AND emr_pat_note_errlog.person_in_charge = '"+ GlobalParameters.getInstance().getLoginUserName() +"' ";
             //�X�K�����_
             String strDischgNoteFinish = " SELECT decode(COUNT(1),0,'Y','N') FROM emr.emr_pat_note_errlog WHERE emr_pat_note_errlog.encounter_no= en.encounter_no AND emr_pat_note_errlog.report_subtype_scid = 'dischgnote' AND emr_pat_note_errlog.person_in_charge = '"+ GlobalParameters.getInstance().getLoginUserName() +"' ";                                    
             //ñ���_
             String strSignStatus = " SELECT decode(COUNT(1),0,'N','Y') FROM emr.emr_diag_confirmlog WHERE emr_diag_confirmlog.encounter_no =en.encounter_no AND emr_diag_confirmlog.return_by IS NULL ";                                            
            
            StringBuffer emrIpdEncounterSQL = new StringBuffer();                     
            emrIpdEncounterSQL.append(" SELECT * FROM (");                         
            //query_type:01:�D�v��v���ۤv���M��
            emrIpdEncounterSQL.append(" SELECT ");
            emrIpdEncounterSQL.append("     '01' AS query_type ");
            emrIpdEncounterSQL.append("     ,("+ strTotalFinish +")AS totalFinish ");
            emrIpdEncounterSQL.append("     ,("+ strAdminNoteFinish +")AS adminNoteFinish ");
            emrIpdEncounterSQL.append("     ,("+ strProgressNoteFinish +")AS progressNoteFinish ");
            emrIpdEncounterSQL.append("     ,("+ strDischgNoteFinish +")AS dischgNoteFinish ");            
            emrIpdEncounterSQL.append("     ,en.* ");
            emrIpdEncounterSQL.append("     ,("+ strSignStatus +")AS signStatus ");
            emrIpdEncounterSQL.append(" FROM ");
            emrIpdEncounterSQL.append("     emr.emr_ipd_encounter en ");
            emrIpdEncounterSQL.append(" WHERE ");
            emrIpdEncounterSQL.append("     en.encounter_no IN("+ sql_1.toString() +") ");                                                                                                                                                            
            emrIpdEncounterSQL.append(" )s WHERE 1=1 ");
            
            if("VS".equalsIgnoreCase(GlobalParameters.getInstance().getDocType()))
            {
                emrIpdEncounterSQL.append(" ORDER BY s.query_type, s.vs_code, s.close_date, s.bed_no ");    
            }
            else
            {
                emrIpdEncounterSQL.append(" ORDER BY s.query_type, s.vs_code, s.bed_no ");
            } 
                        
            ArrayList list = EmrIpdEncounterDAO.getInstance().findAllBySql(emrIpdEncounterSQL.toString());
            emrPatListTable.setModel(new HCTableModel(list, emrPatTableColNames, emrPatTableHeaders, "com.hcsaastech.ehis.doc.vo.EmrIpdEncounterVO", null));
            for(int i=0; i<list.size(); i++)
            {
                EmrIpdEncounterVO vo = (EmrIpdEncounterVO) list.get(i);
                
                if(vo.getEncounterNo().equals(GlobalParameters.getInstance().getEncounterNo()))
                {
                    //����Focus��Rows
                    emrPatListTable.setRowSelectionInterval(i, i);
                    //����Focus��Columns
                    //emrPatListTable.setColumnSelectionInterval(0, 3);
                    emrPatListTable.setColumnSelectionInterval(0, emrPatTableColNames.length-1);
                    break;
                }
            }
            
            //20131129 Jeff �h������:�W�[�d�ߵ��G���A�[�e�J�|������L�k�q�X������            
            emrPatListTable.setAutoResizeMode(0);                        
            emrPatListTable.setFont(new Font(emrPatListTable.getFont().getFontName(), Font.PLAIN, 16));
            emrPatListTable.setRowHeight(25);
                       
            emrPatListTable.getColumnModel().getColumn(0).setPreferredWidth(70);  //�f����chartNo
            emrPatListTable.getColumnModel().getColumn(1).setPreferredWidth(120);  //�f�w�m�WpatName
            emrPatListTable.getColumnModel().getColumn(2).setPreferredWidth(80);  //�ɸ�bedNo            
            emrPatListTable.getColumnModel().getColumn(3).setPreferredWidth(90);  //�D�v��vvsName            
            emrPatListTable.getColumnModel().getColumn(4).setPreferredWidth(90);  //�J�|���admitDate            
            emrPatListTable.getColumnModel().getColumn(5).setPreferredWidth(90);  //�X�|���closeDate            
            emrPatListTable.getColumnModel().getColumn(6).setPreferredWidth(50);  //�J�KadminNoteFinish
            emrPatListTable.getColumnModel().getColumn(7).setPreferredWidth(50);  //���{progressNoteFinish
            emrPatListTable.getColumnModel().getColumn(8).setPreferredWidth(50);  //�X�KdischgNoteFinish   
            emrPatListTable.getColumnModel().getColumn(9).setPreferredWidth(50);  //�����_totalFinish
            emrPatListTable.getColumnModel().getColumn(10).setPreferredWidth(50); //ñ��signStatus
            emrPatListTable.getColumnModel().getColumn(11).setPreferredWidth(110);//��|�Ǹ�encounterNo
                                                              
            DefaultTableCellRenderer cdr = (DefaultTableCellRenderer)emrPatListTable.getDefaultRenderer(Integer.class);
            cdr.setHorizontalAlignment(SwingConstants.LEFT);
            cdr.setVerticalAlignment(SwingConstants.CENTER);                    
            
            emrPatListTable.getColumnModel().getColumn(0).setCellRenderer(cdr);
            emrPatListTable.getColumnModel().getColumn(1).setCellRenderer(cdr);
            emrPatListTable.getColumnModel().getColumn(2).setCellRenderer(cdr);
            emrPatListTable.getColumnModel().getColumn(3).setCellRenderer(cdr);
            emrPatListTable.getColumnModel().getColumn(4).setCellRenderer(cdr);
            emrPatListTable.getColumnModel().getColumn(5).setCellRenderer(cdr);
            emrPatListTable.getColumnModel().getColumn(6).setCellRenderer(cdr);
            emrPatListTable.getColumnModel().getColumn(7).setCellRenderer(cdr);
            emrPatListTable.getColumnModel().getColumn(8).setCellRenderer(cdr);
            emrPatListTable.getColumnModel().getColumn(9).setCellRenderer(cdr);
            emrPatListTable.getColumnModel().getColumn(10).setCellRenderer(cdr);
            emrPatListTable.getColumnModel().getColumn(11).setCellRenderer(cdr);
                                              
            //Win7 1.5��Java�|��TimeZone���D
            //TimeZone.setDefault(TimeZone.getTimeZone("Asia/Taipei"));            
        }
        catch (Exception ex)
        {
            MessageManager.showException(ex.getMessage(), ex);
            logger.debug(ex.getMessage(), ex);
        }
    }
    
    /**
     * Ū���f�w�M��
     */
    public void loadEmrPatList()
    {
        try
        {
            loadEmrPatListF = "loadEmrPatList";
            //CHECK_01: �D�v��v���o����
            //CHECK_02�G �Y��|�_��ܤw�X�|�A�ѼƳ̤j���i�W�L500
        	
            Calendar _dischgDate = Calendar.getInstance(); //���o�t�Τ��
        	        	    	
            //�z��X�|����_
            String _dischgStDate;             	
            _dischgDate.setTime(new Date());
            _dischgStDate = DateUtil.getDateString(_dischgDate.getTime(), "yyyy-MM-dd");
    		
            //�z��X�|�����
            int dischgDays = -7;
            //System.out.println("dischgDays: " + dischgDays);
            if(!StringUtil.isBlank(txtDischgDays.getText().trim()))
            {
                dischgDays = -(Integer.parseInt(txtDischgDays.getText().trim()));
            }
            //System.out.println("dischgDays: " + dischgDays);        	
        	
            String _dischgEdDate; 
            _dischgDate.add(Calendar.DATE, dischgDays);
            _dischgEdDate = DateUtil.getDateString(_dischgDate.getTime(), "yyyy-MM-dd");
            //System.out.println("_dischgStDate: " + _dischgStDate);
            //System.out.println("_dischgEdDate: " + _dischgEdDate);
        	
            String vsCodeValue = vsCodeField.getText(); //�D�v��v
            String deptCodeValue = deptCodeField.getText(); //��O
            String branchCodeValue = branchCodeField.getText(); //�@�z��
            String encounterNoValue = encounterNoField.getText(); //��|�Ǹ�
            String chartNoValue = chartNoField.getText(); //�f�����X
            String patNameValue = patNameField.getText(); //�m�W
            String bedNoValue = bedNoField.getText(); //�ɦ�
            String admitDateValue = admitDateField.getText(); //��|���
          
            boolean vsCodeFlag = !StringUtil.isBlank(vsCodeValue);
            boolean deptCodeFlag = !StringUtil.isBlank(deptCodeValue);
            boolean branchCodeFlag = !StringUtil.isBlank(branchCodeValue);
            boolean encounterNoFlag = !StringUtil.isBlank(encounterNoValue);
            boolean chartNoFlag = !StringUtil.isBlank(chartNoValue);
            boolean patNameFlag = !StringUtil.isBlank(patNameValue);
            boolean bedNoFlag = !StringUtil.isBlank(bedNoValue);
            boolean admitDateFlag = !StringUtil.isBlank(admitDateValue);        	        	        	        	        	        	             	       
        	                                                                    
            /**
             * 1.�d�߯f�w�M��
             * 2.�v������f_check_medical_chart
             * 3.��f�w�M��[�W�]�ֵ�
             * */
            
            Connection conn; 
            Statement stmt = null;
            ResultSet rs = null;
            
            String sql = "";
            StringBuffer sql_1 = new StringBuffer();
            StringBuffer sql_2 = new StringBuffer();
            
            try
            {            
                conn = DBConnectionHelper.getInstance().getMySqlConnection();
                stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                                                        
                sql_1.append(" SELECT a.encounter_no FROM emr.emr_ipd_encounter a WHERE 1 = 1 ");                        
                if(vsCodeFlag || deptCodeFlag || branchCodeFlag || encounterNoFlag || chartNoFlag || patNameFlag && bedNoFlag || admitDateFlag)
                {
                    sql_1.append((encounterNoFlag)?" AND a.encounter_no = '"+ encounterNoValue.trim() +"' ":"");               
                    sql_1.append((chartNoFlag)?"     AND a.chart_no     = '"+ chartNoValue.trim() +"' ":"");
                    sql_1.append((patNameFlag)?"     AND a.pat_name     = '"+ patNameValue.trim() +"' ":"");
                    sql_1.append((bedNoFlag)?"       AND a.bed_no       = '"+ bedNoValue.trim() +"' ":"");
                    sql_1.append((deptCodeFlag)?"    AND a.dept_code    = '"+ deptCodeValue.trim() +"' ":"");
                    sql_1.append((vsCodeFlag)?"      AND a.vs_code      = '"+ vsCodeValue.trim() +"' ":"");
                    sql_1.append((branchCodeFlag)?"  AND a.branch_code  = '"+ branchCodeValue.trim() +"' ":"");
                    sql_1.append((admitDateFlag)?"   AND (a.admit_date BETWEEN to_date('"+ admitDateValue.trim() +" 00:00:00', 'yyyy-mm-dd hh24:mi:ss') AND to_date('"+ admitDateValue.trim() +" 23:59:59', 'yyyy-mm-dd hh24:mi:ss')) ":"");               
                }            
                else
                {//����J���̬d�߱���A���קK�d�߹L�j�A�����w�]�d�߱���                    
                    if("28".equals(GlobalParameters.getInstance().getSecUserVO().getDeptCode()))
                    {//�Y�n�J������15F ICU�A�H�@�z���d��                        
                        sql_1.append(" AND (bed_no LIKE'a15%' OR bed_no IN('A03112', 'A03113', 'A03212', 'A03213')) ");
                    }                
                    else if("2A".equals(GlobalParameters.getInstance().getSecUserVO().getDeptCode()))
                    {                        
                        sql_1.append(" AND (bed_no LIKE'A03%' AND bed_no NOT IN('A03112', 'A03113', 'A03212', 'A03213')) ");
                    }
                    else if("VS".equals(GlobalParameters.getInstance().getDocType()))
                    {//�Y�n�J�̬�VS                        
                        vsCodeValue = GlobalParameters.getInstance().getSecUserVO().getUsername();
                        System.out.println(vsCodeValue);
                        sql_1.append(" AND vs_code = '" + vsCodeValue + "' ");                        
                        vsCodeFlag = true;
                    }                
                    else
                    {//�䥦                        
                        sql_1.append(" AND dept_code = '" + GlobalParameters.getInstance().getSecUserVO().getDeptCode() + "' ");                    
                    }                                          
                }
                
                //��|�_
                if(beInHospitalFlag)
                {
                        sql_1.append(" AND a.pat_state = 'A' ");
                }
                else
                {                                   
                    //sql_1.append(" AND a.pat_state <> 'A' ");
                    sql_1.append(" AND a.pat_state NOT IN('A','CA') ");
                    sql_1.append(" AND (a.close_date BETWEEN to_date('" + _dischgEdDate + " 00:00:00', 'yyyy-mm-dd hh24:mi:ss') AND to_date('" + _dischgStDate + " 23:59:59', 'yyyy-mm-dd hh24:mi:ss')) "); 
                }                
                sql = sql_1.toString();
                
                if(vsCodeFlag)
                {//qStringBuffer sql_1 = new StringBuffer();uery_type:02:�D�v��v��������ñ����v���ۤv���M��
                    sql_2.append(" SELECT DISTINCT b.encounter_no FROM emr.emr_pat_note b, emr.emr_ipd_encounter c WHERE 1 = 1 ");
                    
                    if(beInHospitalFlag)
                    {
                            sql_2.append(" AND c.Pat_State = 'A' ");
                    }
                    else
                    {                                       
                            //sql_2.append(" AND c.Pat_State <> 'A' ");
                            sql_2.append(" AND c.Pat_State NOT IN('A','CA') ");
                            sql_2.append(" AND (c.close_date BETWEEN to_date('" + _dischgEdDate + " 00:00:00', 'yyyy-mm-dd hh24:mi:ss') AND to_date('" + _dischgStDate + " 23:59:59', 'yyyy-mm-dd hh24:mi:ss')) "); 
                    }                               
                    sql_2.append(" AND c.encounter_no = b.encounter_no ");
                    sql_2.append(" AND c.vs_code != b.person_in_charge ");
                    sql_2.append(" AND b.person_in_charge = '"+ vsCodeValue.trim() +"' ");
                    sql_2.append(" AND b.report_subtype_scid IN('adminnote', 'progressnote', 'dischgnote') ");
                    
                    sql = sql + "UNION" + sql_2.toString();
                }
                                                                
                rs = stmt.executeQuery(sql.toString());                 
                CallableStatement stproc_stmt = conn.prepareCall("{call P_CHECK_MEDICAL_CHART(?,?)}");
                while(rs.next())
                {
                    stproc_stmt.registerOutParameter(2, OracleTypes.VARCHAR);
                    stproc_stmt.setString(1, rs.getString("encounter_no"));
                    stproc_stmt.execute();
                }
                rs.close();
                stproc_stmt.close();
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(this, "loadEmrPatList Exception: " + e.toString());
            }
            finally
            {
                DBConnectionHelper.getInstance().cleanRsAndStmt(rs,stmt);           
            }
                                                    
            //�`�駹���_
            String strTotalFinish = " SELECT decode(COUNT(1),0,'Y','N') FROM emr.emr_pat_note_errlog WHERE emr_pat_note_errlog.encounter_no= en.encounter_no ";            
            //�J�K�����_
            String strAdminNoteFinish = " SELECT decode(COUNT(1),0,'Y','N') FROM emr.emr_pat_note_errlog WHERE emr_pat_note_errlog.encounter_no= en.encounter_no AND emr_pat_note_errlog.report_subtype_scid = 'adminnote' ";            
            //���{�����_                             
            String strProgressNoteFinish = " SELECT decode(COUNT(1),0,'Y','N') FROM emr.emr_pat_note_errlog WHERE emr_pat_note_errlog.encounter_no= en.encounter_no AND emr_pat_note_errlog.report_subtype_scid = 'progressnote' ";        
            //�X�K�����_
            String strDischgNoteFinish = " SELECT decode(COUNT(1),0,'Y','N') FROM emr.emr_pat_note_errlog WHERE emr_pat_note_errlog.encounter_no= en.encounter_no AND emr_pat_note_errlog.report_subtype_scid = 'dischgnote' ";                                
            //ñ���_
            String strSignStatus = " SELECT decode(COUNT(1),0,'N','Y') FROM emr.emr_diag_confirmlog WHERE emr_diag_confirmlog.encounter_no =en.encounter_no AND emr_diag_confirmlog.return_by IS NULL ";
            
            StringBuffer emrIpdEncounterSQL = new StringBuffer();                     
            emrIpdEncounterSQL.append(" SELECT * FROM (");                         
            //query_type:01:�D�v��v���ۤv���M��
            emrIpdEncounterSQL.append(" SELECT ");
            emrIpdEncounterSQL.append("     '01' AS query_type ");
            emrIpdEncounterSQL.append("     ,("+ strTotalFinish +")AS totalFinish ");
            emrIpdEncounterSQL.append("     ,("+ strAdminNoteFinish +")AS adminNoteFinish ");
            emrIpdEncounterSQL.append("     ,("+ strProgressNoteFinish +")AS progressNoteFinish ");
            emrIpdEncounterSQL.append("     ,("+ strDischgNoteFinish +")AS dischgNoteFinish ");            
            emrIpdEncounterSQL.append("     ,en.* ");
            emrIpdEncounterSQL.append("     ,("+ strSignStatus +")AS signStatus ");
            emrIpdEncounterSQL.append(" FROM ");
            emrIpdEncounterSQL.append("     emr.emr_ipd_encounter en ");
            emrIpdEncounterSQL.append(" WHERE ");
            emrIpdEncounterSQL.append("     en.encounter_no IN("+ sql_1.toString() +") ");
                                                                    
            if(vsCodeFlag)
            {
                //�`�駹���_
                String strTotalFinish_1 = " SELECT decode(COUNT(1),0,'Y','N') FROM emr.emr_pat_note_errlog WHERE emr_pat_note_errlog.encounter_no= en.encounter_no AND emr_pat_note_errlog.person_in_charge = '"+ vsCodeValue +"' ";
                //�J�K�����_
                String strAdminNoteFinish_1 = " SELECT decode(COUNT(1),0,'Y','N') FROM emr.emr_pat_note_errlog WHERE emr_pat_note_errlog.encounter_no= en.encounter_no AND emr_pat_note_errlog.report_subtype_scid = 'adminnote' AND emr_pat_note_errlog.person_in_charge = '"+ vsCodeValue +"' ";
                //���{�����_
                String strProgressNoteFinish_1 = " SELECT decode(COUNT(1),0,'Y','N') FROM emr.emr_pat_note_errlog WHERE emr_pat_note_errlog.encounter_no= en.encounter_no AND emr_pat_note_errlog.report_subtype_scid = 'progressnote' AND emr_pat_note_errlog.person_in_charge = '"+ vsCodeValue +"' ";
                //�X�K�����_
                String strDischgNoteFinish_1 = " SELECT decode(COUNT(1),0,'Y','N') FROM emr.emr_pat_note_errlog WHERE emr_pat_note_errlog.encounter_no= en.encounter_no AND emr_pat_note_errlog.report_subtype_scid = 'dischgnote' AND emr_pat_note_errlog.person_in_charge = '"+ vsCodeValue +"' ";
                
                //query_type:02:�D�v��v��������ñ����v���ۤv���M��
            	emrIpdEncounterSQL.append(" UNION ");
            	emrIpdEncounterSQL.append(" SELECT ");
                emrIpdEncounterSQL.append("     '02' AS query_type ");
                emrIpdEncounterSQL.append("     ,("+ strTotalFinish_1 +")AS totalFinish ");
                emrIpdEncounterSQL.append("     ,("+ strAdminNoteFinish_1 +")AS adminNoteFinish ");
                emrIpdEncounterSQL.append("     ,("+ strProgressNoteFinish_1 +")AS progressNoteFinish ");
                emrIpdEncounterSQL.append("     ,("+ strDischgNoteFinish_1 +")AS dischgNoteFinish ");            
                emrIpdEncounterSQL.append("     ,en.* ");
                emrIpdEncounterSQL.append("     ,("+ strSignStatus +")AS signStatus ");
                emrIpdEncounterSQL.append(" FROM ");
                emrIpdEncounterSQL.append("     emr.emr_ipd_encounter en ");
                emrIpdEncounterSQL.append(" WHERE ");
                emrIpdEncounterSQL.append("     en.encounter_no IN("+ sql_2.toString() +") ");                                            
            }
                        
            emrIpdEncounterSQL.append(" )s WHERE 1=1 ");
            
            String showStr = (String)beCompleteComboBox.getSelectedItem();
            if("����".equals(showStr))
            {
            	emrIpdEncounterSQL.append(" AND s.totalFinish = 'Y' ");            	                
            }
            else if("������".equals(showStr))
            {
            	emrIpdEncounterSQL.append(" AND s.totalFinish = 'N' ");                
            }
            
            String signStr = (String)beSignComboBox.getSelectedItem();
            if("ñ��".equals(signStr))
            {
                emrIpdEncounterSQL.append(" AND s.signStatus = 'Y' ");                                 
            }
            else if("��ñ��".equals(signStr))
            {
                //emrIpdEncounterSQL.append(" AND s.signStatus = 'N' ");                
                emrIpdEncounterSQL.append(" AND ((s.signStatus = 'N' AND s.totalFinish = 'N') OR (s.signStatus = 'N' AND s.totalFinish = 'Y' AND s.vs_code = '"+ vsCodeValue.trim() +"')) ");                
            }                    
                                            
            if("VS".equalsIgnoreCase(GlobalParameters.getInstance().getDocType()))
            {
                emrIpdEncounterSQL.append(" ORDER BY s.query_type, s.vs_code, s.close_date, s.bed_no ");    
            }
            else
            {
                emrIpdEncounterSQL.append(" ORDER BY s.query_type, s.vs_code, s.bed_no ");
            }                    
            
            ArrayList list = EmrIpdEncounterDAO.getInstance().findAllBySql(emrIpdEncounterSQL.toString());
            System.out.print(emrIpdEncounterSQL.toString());
            HCTableModel model = new HCTableModel(list, emrPatTableColNames, emrPatTableHeaders, "com.hcsaastech.ehis.doc.vo.EmrIpdEncounterVO", null);
            emrPatListTable.setModel(model);
            for(int i=0; i<list.size(); i++)
            {
                EmrIpdEncounterVO vo = (EmrIpdEncounterVO) list.get(i);
                
                if(vo.getEncounterNo().equals(GlobalParameters.getInstance().getEncounterNo()))
                {
                    //����Focus��Rows
                    emrPatListTable.setRowSelectionInterval(i, i);
                    //����Focus��Columns
                    //emrPatListTable.setColumnSelectionInterval(0, 3);
                    emrPatListTable.setColumnSelectionInterval(0, emrPatTableColNames.length-1);
                    break;
                }
            }
            
            //20131129 Jeff �h������:�W�[�d�ߵ��G���A�[�e�J�|������L�k�q�X������            
            emrPatListTable.setAutoResizeMode(0);                        
            emrPatListTable.setFont(new Font(emrPatListTable.getFont().getFontName(), Font.PLAIN, 16));
            emrPatListTable.setRowHeight(25);
                                                       
            emrPatListTable.getColumnModel().getColumn(0).setPreferredWidth(70);  //�f����chartNo
            emrPatListTable.getColumnModel().getColumn(1).setPreferredWidth(120);  //�f�w�m�WpatName
            emrPatListTable.getColumnModel().getColumn(2).setPreferredWidth(80);  //�ɸ�bedNo            
            emrPatListTable.getColumnModel().getColumn(3).setPreferredWidth(90);  //�D�v��vvsName            
            emrPatListTable.getColumnModel().getColumn(4).setPreferredWidth(90);  //�J�|���admitDate            
            emrPatListTable.getColumnModel().getColumn(5).setPreferredWidth(90);  //�X�|���closeDate            
            emrPatListTable.getColumnModel().getColumn(6).setPreferredWidth(50);  //�J�KadminNoteFinish
            emrPatListTable.getColumnModel().getColumn(7).setPreferredWidth(50);  //���{progressNoteFinish
            emrPatListTable.getColumnModel().getColumn(8).setPreferredWidth(50);  //�X�KdischgNoteFinish   
            emrPatListTable.getColumnModel().getColumn(9).setPreferredWidth(50);  //�����_totalFinish
            emrPatListTable.getColumnModel().getColumn(10).setPreferredWidth(50); //ñ��signStatus
            emrPatListTable.getColumnModel().getColumn(11).setPreferredWidth(110);//��|�Ǹ�encounterNo
                                                              
            DefaultTableCellRenderer cdr = (DefaultTableCellRenderer)emrPatListTable.getDefaultRenderer(Integer.class);
            cdr.setHorizontalAlignment(SwingConstants.LEFT);
            cdr.setVerticalAlignment(SwingConstants.CENTER);                    
            
            emrPatListTable.getColumnModel().getColumn(0).setCellRenderer(cdr);
            emrPatListTable.getColumnModel().getColumn(1).setCellRenderer(cdr);
            emrPatListTable.getColumnModel().getColumn(2).setCellRenderer(cdr);
            emrPatListTable.getColumnModel().getColumn(3).setCellRenderer(cdr);
            emrPatListTable.getColumnModel().getColumn(4).setCellRenderer(cdr);
            emrPatListTable.getColumnModel().getColumn(5).setCellRenderer(cdr);
            emrPatListTable.getColumnModel().getColumn(6).setCellRenderer(cdr);
            emrPatListTable.getColumnModel().getColumn(7).setCellRenderer(cdr);
            emrPatListTable.getColumnModel().getColumn(8).setCellRenderer(cdr);
            emrPatListTable.getColumnModel().getColumn(9).setCellRenderer(cdr);
            emrPatListTable.getColumnModel().getColumn(10).setCellRenderer(cdr);
            emrPatListTable.getColumnModel().getColumn(11).setCellRenderer(cdr);
                                    
            //TableSorter sorter = new TableRowSorter(model);  
            //emrPatListTable.setRowSorter(sorter);            
                                              
            //Win7 1.5��Java�|��TimeZone���D
            //TimeZone.setDefault(TimeZone.getTimeZone("Asia/Taipei"));
        }
        catch (Exception ex)
        {
            MessageManager.showException(ex.getMessage(), ex);
            logger.debug(ex.getMessage(), ex);
        }
    }
    
    /**
     * 
     */
    public void loadPatNoteData()
    {
        try
        {
            String sql = "SELECT * FROM emr_pat_note ";
            
            if(GlobalParameters.getInstance().getDocumentNo() != null &&
            		!GlobalParameters.getInstance().getDocumentNo().equals("null"))
            {
                sql += "WHERE chart_no = '" + GlobalParameters.getInstance().getChartNo() + "' ";
                sql += "AND document_number = '" + GlobalParameters.getInstance().getDocumentNo() +  "' ";
            }
            else
            {
                sql += "WHERE encounter_no = '" + GlobalParameters.getInstance().getEncounterNo() + "' ";
                sql += "AND sheet_id = '" + filterString + "' ";
            }
            
            sql += "ORDER BY report_date desc";
            //System.out.println("sql:" + sql);            
            
            ArrayList list = EmrPatNoteDAO.getInstance().findAllBySql(sql);
            //System.out.println("data count:" + list.size());            
            
            //String[] colNames = new String[] { "displayName" };
            //String[] headers = new String[] { "���W��" };
            
            String[] colNames;
            String[] headers;
            if("DischgNote".equals(filterString))
            {
            	/*
            	colNames = new String[] { "reportDateStr","enteredByName","reportStatusName","reportThirdTypeCode","lastUpdatedByName","personInChargeName" };
                headers = new String[] { "�f�����","�إߪ�","���A","��","���ʪ�","��ñ����v"};
                */                
                colNames = new String[] { "reportDateStr","reportStatusName","reportThirdTypeCode","personInChargeName","lastUpdatedByName","createByName" };
                headers = new String[] { "�f�����","���A","��","��ñ����v","���ʪ�","�إߪ�"};                
            }
            else if("ProgressNote".equals(filterString))
            {
            	/*
            	colNames = new String[] { "reportDateStr","enteredByName","reportStatusName","reportThirdTypeCode","lastUpdatedByName","commNeedFlagDesc","personInChargeName" };                
                headers = new String[] { "�f�����","�إߪ�","���A","��","���ʪ�","�w���y","��ñ����v" };
                */
                colNames = new String[] { "reportDateStr","reportStatusName","reportThirdTypeCode","commNeedFlagDesc","personInChargeName","lastUpdatedByName","createByName" };                
                headers = new String[] { "�f�����","���A","��","�w���y","��ñ����v","���ʪ�","�إߪ�" };
            }            	
            else
            {
            	colNames = new String[] { "reportDateStr","reportStatusName","personInChargeName","lastUpdatedByName","createByName"};
                headers = new String[] { "�f�����","���A","��ñ����v","���ʪ�","�إߪ�"};
            }
                                               
            //�����J�e����Ц�m
            int defaultRowSelected = emrPatNoteTable.getSelectedRow();
            emrPatNoteTable.setModel(new HCTableModel(list, colNames, headers,
            		"com.hcsaastech.ehis.doc.vo.EmrPatNoteVO", null));
                        
            
            
            DefaultTableCellRenderer cdr = (DefaultTableCellRenderer)emrPatNoteTable.getDefaultRenderer(Integer.class);
            cdr.setHorizontalAlignment(SwingConstants.LEFT);                        
            
            int reportDateW = 80;
            int enteredByW = 50;
            int statusByW = 40;
            int thirdTypeW = 20;
            int lastUpdateByW = 50;
            int commNeedW = 50;
            
            if("DischgNote".equals(filterString))
            {
            	emrPatNoteTable.setAutoResizeMode(0);
            	emrPatNoteTable.setRowHeight(25);
            	emrPatNoteTable.setFont(new Font(emrPatListTable.getFont().getFontName(), Font.PLAIN, 16));
            	
                emrPatNoteTable.getColumnModel().getColumn(0).setPreferredWidth(120); //���i���
                emrPatNoteTable.getColumnModel().getColumn(1).setPreferredWidth(40);  //���A
                emrPatNoteTable.getColumnModel().getColumn(2).setPreferredWidth(50);  //�l���O
                emrPatNoteTable.getColumnModel().getColumn(3).setPreferredWidth(80);  //��ñ����v
                emrPatNoteTable.getColumnModel().getColumn(4).setPreferredWidth(80);  //���ʪ�
                emrPatNoteTable.getColumnModel().getColumn(5).setPreferredWidth(80);  //�إߪ�
                
                emrPatNoteTable.getColumnModel().getColumn(0).setCellRenderer(cdr);
                emrPatNoteTable.getColumnModel().getColumn(1).setCellRenderer(cdr);
                emrPatNoteTable.getColumnModel().getColumn(2).setCellRenderer(cdr);
                emrPatNoteTable.getColumnModel().getColumn(3).setCellRenderer(cdr);
                emrPatNoteTable.getColumnModel().getColumn(4).setCellRenderer(cdr);
                emrPatNoteTable.getColumnModel().getColumn(5).setCellRenderer(cdr);
            }
            else if("ProgressNote".equals(filterString))
            {                      
            	emrPatNoteTable.setAutoResizeMode(0);
            	emrPatNoteTable.setRowHeight(25);
            	emrPatNoteTable.setFont(new Font(emrPatListTable.getFont().getFontName(), Font.PLAIN, 16));
            	
            	emrPatNoteTable.getColumnModel().getColumn(0).setPreferredWidth(120); //���i���
                emrPatNoteTable.getColumnModel().getColumn(1).setPreferredWidth(40);  //���A
                emrPatNoteTable.getColumnModel().getColumn(2).setPreferredWidth(30);  //�l���O
                emrPatNoteTable.getColumnModel().getColumn(3).setPreferredWidth(50);  //�ݵ��y�_
                emrPatNoteTable.getColumnModel().getColumn(4).setPreferredWidth(80);  //��ñ����v
                emrPatNoteTable.getColumnModel().getColumn(5).setPreferredWidth(80);  //���ʪ�
                emrPatNoteTable.getColumnModel().getColumn(6).setPreferredWidth(80);  //�إߪ�
                
                emrPatNoteTable.getColumnModel().getColumn(0).setCellRenderer(cdr);
                emrPatNoteTable.getColumnModel().getColumn(1).setCellRenderer(cdr);
                emrPatNoteTable.getColumnModel().getColumn(2).setCellRenderer(cdr);
                emrPatNoteTable.getColumnModel().getColumn(3).setCellRenderer(cdr);
                emrPatNoteTable.getColumnModel().getColumn(4).setCellRenderer(cdr);                
                emrPatNoteTable.getColumnModel().getColumn(5).setCellRenderer(cdr);
                emrPatNoteTable.getColumnModel().getColumn(6).setCellRenderer(cdr);  //�ݵ��y�_
            }            	
            else
            {
            	emrPatNoteTable.setAutoResizeMode(0);
            	emrPatNoteTable.setRowHeight(25);
            	emrPatNoteTable.setFont(new Font(emrPatListTable.getFont().getFontName(), Font.PLAIN, 16));            	            
                
                emrPatNoteTable.getColumnModel().getColumn(0).setPreferredWidth(120); //���i���
                emrPatNoteTable.getColumnModel().getColumn(1).setPreferredWidth(40);  //���A
                emrPatNoteTable.getColumnModel().getColumn(2).setPreferredWidth(80);  //��ñ����v                
                emrPatNoteTable.getColumnModel().getColumn(3).setPreferredWidth(80);  //���ʪ�
                emrPatNoteTable.getColumnModel().getColumn(4).setPreferredWidth(80);  //�إߪ�
                
                emrPatNoteTable.getColumnModel().getColumn(0).setCellRenderer(cdr);
                emrPatNoteTable.getColumnModel().getColumn(1).setCellRenderer(cdr);
                emrPatNoteTable.getColumnModel().getColumn(2).setCellRenderer(cdr);
                emrPatNoteTable.getColumnModel().getColumn(3).setCellRenderer(cdr);
                emrPatNoteTable.getColumnModel().getColumn(4).setCellRenderer(cdr);
            }
                                    
            if(list.size() > 0)
            {
                //�N��а��d�b�쥻����m
                if(defaultRowSelected == -1)
                {
                    emrPatNoteTable.setRowSelectionInterval(0, 0);
                }
                else if(defaultRowSelected >= emrPatNoteTable.getRowCount())
                {
                    emrPatNoteTable.setRowSelectionInterval(emrPatNoteTable.getRowCount() - 1, emrPatNoteTable.getRowCount() - 1);
                }
                else
                {
                    emrPatNoteTable.setRowSelectionInterval(defaultRowSelected, defaultRowSelected);
                }
                emrPatNoteTable.setColumnSelectionInterval(0, 0);
                currentVO = (EmrPatNoteVO) emrPatNoteTable.getSelectedVO();
                
                //System.out.println("@@getRowStatus: " + currentVO.getRowStatus());
                
                setEkitCoreDocumentId(currentVO.getDocumentId());
                setEventTimeTextValue();
                
                executeProgressNoteRule(currentVO);
                executeDischgNoteRule(currentVO);
                                                                              
                //��ܳd����v
                this.txtPersonInChargeName.setText(DateUtil.getDocName(currentVO.personInCharge));
                this.txtPersonInChargeCode.setText(currentVO.personInCharge);
            }
        }
        catch (Exception ex)
        {
            MessageManager.showException(ex.getMessage(), ex);
            logger.debug(ex.getMessage(), ex);
        }
    }

    public void loadEmrIpdEncounterData(String encounterNo)
    {
        try
        {
            String sql = "select * from emr_ipd_encounter where encounter_no = '" + encounterNo + "'";
            ArrayList<?> list = EmrIpdEncounterDAO.getInstance().findAllBySql(sql);

            if(list.size() > 0)
            {
                GlobalParameters.getInstance().setEmrIpdEncounterVO((EmrIpdEncounterVO)list.get(0));                            
                
                //����y���{�O���z�ˬd
                this.executeProgressNoteRule(currentVO);                               
            }
            else
            {
            	logger.info("[�L�f�w�򥻸��] SQL:" + sql);
            }
        }
        catch (Exception ex)
        {
            MessageManager.showException(ex.getMessage(), ex);
            logger.debug(ex.getMessage(), ex);
        }
    }
    
    /**
     * �d��User����
     */
    public void loadSecUserData()
    {
        try
        {
            String sql = "select * from sec_user where username = '" + GlobalParameters.getInstance().getLoginUserName() + "'";
            ArrayList<?> list = SecUserDAO.getInstance().findAllBySql(sql);
            
            if(list.size() > 0)
            {
                GlobalParameters.getInstance().setSecUserVO((SecUserVO)list.get(0));
            }
        }
        catch (Exception ex)
        {
            MessageManager.showException(ex.getMessage(), ex);
            logger.debug(ex.getMessage(), ex);
        }
    }
    
    /**
     * ���J�v���B�m�O����ExamOrderDesc
     */
    private void loadExamOrderDesc(){
        try
        {
            ArrayList ierOrderList = new ArrayList();
            ierOrderList.add(new HCComboBoxItem("�v���B�m", "�v���B�m"));  //Default
            
            String sql = "";
            sql = sql + " SELECT ";
            sql = sql + "   a.* ";
            sql = sql + " FROM ";
            sql = sql + "   ier_order a, ";
            sql = sql + "   com.com_ordermst_emr_view b ";
            sql = sql + " WHERE ";
            sql = sql + "   a.encounter_no = '"+ GlobalParameters.getInstance().getEncounterNo() +"' ";
            sql = sql + "   AND a.order_state = 'A' ";
            sql = sql + "   AND a.order_type = 'TRE' ";
            sql = sql + "   AND a.order_code = b.order_code ";
            sql = sql + "   AND (instr(b.exam_type,'TRE01') != 0 OR instr(b.exam_type,'TRE07') != 0) ";
            sql = sql + "   AND instr(b.exam_type,'TRE0111') = 0 ";
            sql = sql + "   Order by a.display_name ";
                                                    
            ArrayList ierOrderData = IerOrderDAO.getInstance().findAllBySql(sql);                                
            for(int i=0; i<ierOrderData.size(); i++)
            {
                String displayName = ((IerOrderVO)ierOrderData.get(i)).getDisplayName();
                HCComboBoxItem item = new HCComboBoxItem(displayName, displayName);
                if( !ierOrderList.contains(item) )
                {
                    ierOrderList.add(item);
                }
            }
            
            HCComboBoxModel hcbm = new HCComboBoxModel(ierOrderList, "label", "com.hcsaastech.ehis.doc.common.HCComboBoxItem");                                                        
            treNoteComboBox.setModel(hcbm);
            treNoteComboBox.setSelectedItem(ierOrderList.get(0));
        }
        catch(Exception e)
        {      
            MessageManager.showException(e.getMessage(),e);
            logger.error(e.getMessage(),e);
        }
    }                    
       
    /**
     * �]�w�������󪺰ʧ@<br>
     * 1.�f�����O�U�Ԥ����ʧ@<br>
     * 2.�f�����ƹ��I���ʧ@<br>
     * 3.�f�w�M��ƹ��I���ʧ@<br>
     * 4.TabbedPane�����ʧ@<br>
     * 5.���`�M��ƹ��I���ƥ�<br>
     * 6.ekit�[�Jkey��ť��<br>
     * 7.Weekly summary JComboBox Action Event<br>
     */
    public void setAction()
    {
        //�I��X���������G����currentVO���s���v��
        sourceFrame.addWindowListener(new java.awt.event.WindowAdapter()
        {
            public void windowClosing(java.awt.event.WindowEvent windowEvent)
            {
                checkInDocument();
                System.exit(0);
                /*
                if(JOptionPane.showConfirmDialog(sourceFrame, "Are you sure to close this window?", "Really Closing?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
                {                
                    System.exit(0);
                }
                */
            }
        });
        
        //�f�����O�U�Ԥ����ʧ@
        noteCategoryComboBox.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                boolean flag = checkData();
                if(flag)
                	return;
                
                if(noteCategoryComboBox.getSelectedItem() != null)
                {
                    //�]�w�Ѽ�
                    EmrNoteCategoryVO vo = (EmrNoteCategoryVO)((HCComboBoxModel)noteCategoryComboBox.getModel()).getVO();
                    loadChapterData(vo.getId());
                    filterString = vo.getNoteType();
                    
                    //logger.debug("NoteType::" + filterString);
                    
                    if("OpNote".equals(filterString) && !("012004".equals(GlobalParameters.getInstance().getLoginUserName())))
                    {
                        addBtn.setEnabled(false);
                        delBtn.setEnabled(false);
                    }
                    else
                    {
                        addBtn.setEnabled(true);
                        delBtn.setEnabled(true);
                    }
                    
                    //�B�m�W��´��J:��ܳB�m�ɤ~�|���
                    if("TreNote".equals(filterString))
                    {
                        loadExamOrderDesc();
                        lblTreNoteName.setVisible(true);
                        treNoteComboBox.setVisible(true);
                    }
                    else
                    {
                        lblTreNoteName.setVisible(false);
                        treNoteComboBox.setVisible(false);
                    }
                    
                    lbldtlKind.setVisible(false);
                                                           
                    if("ProgressNote".equals(filterString)) {
                    	lbldtlKind.setVisible(true);                    	
                    	wkSummaryComboBox.setVisible(true);
                    } else {                    	
                    	wkSummaryComboBox.setVisible(false);
                    }
                    
                    if("DischgNote".equals(filterString)) {
                    	lbldtlKind.setVisible(true);
                    	dischgNoteComboBox.setVisible(true);
                    } else {                    	
                    	dischgNoteComboBox.setVisible(false);
                    }
                    
                    setBinding();
                    
                    //�����
                    String _noteChapter = ((EmrNoteChapterVO)emrNoteChapterData.get(0)).getNoteChapter();
                    //logger.debug("NoteChapter::" + _noteChapter);
                    //loadPatNoteData();
                    noteDataObjectPanel.loadDataobjectData(vo.getId().toString(), _noteChapter);
                    noteDataObjectPanel.setNoteCategory(filterString);
                    noteTemplatePanel.loadTemplatemstData(vo.getNoteType());
                    if(noteTemplateVsPanel != null) {
                        noteTemplateVsPanel.loadTemplatemstData(vo.getNoteType());
                    }
                    noteTemplateHosPanel.loadTemplatemstData(vo.getNoteType());
                    
                    //��l�e��
                    rollBack();
                    initNoteTextData();
                }
            }
        });
        
        //�f�����ƹ��I���ʧ@
        emrPatNoteTable.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e)
            {
                FunctionUtil functionUtil = new FunctionUtil();
                String editByCode = functionUtil.getEditByCode(currentVO.getId());
                
                //�I���G�U��A�����ӳ��`����
                if(e.getClickCount() == 2)
                {
                    //���QCHECK OUT: �i�s��
                    if("N".equals(editByCode))
                    {
                        jideTabbedPane.setSelectedIndex(2);                        
//                        changeTextPanel(jideTabbedPane.getSelectedIndex());
//                        addBtn.setEnabled(false);
//                        delBtn.setEnabled(false);
                    }
                    else
                    {
                         String editByName = functionUtil.getEditByName(editByCode);
                         JOptionPane.showMessageDialog(Utils.getParentFrame((JComponent)e.getSource()), "�����f����ƥثe��<"+ editByName +">�s�褤","�����f����ƥثe��<"+ editByName +">�s�褤", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                //�I���@�U
                else
                {
                    boolean flag = checkData();
                    //System.out.println("@currentVO.getRowStatus(): " + currentVO.getRowStatus());
                    if(flag) {
                    	int rows = emrPatNoteTable.getRowCount();
                    	for(int i=0;i<rows;i++) {
                    		EmrPatNoteVO tmpVo = (EmrPatNoteVO) (((HCTableModel)emrPatNoteTable.getModel()).getVO(i));
                        	if(currentVO == tmpVo) {
                        		emrPatNoteTable.setRowSelectionInterval(i, i);
                        		break;
                        	}
                    	}
                    	return;
                    }
                    EmrPatNoteVO vo = (EmrPatNoteVO) emrPatNoteTable.getSelectedVO();
                    //System.out.println("CurrentVO :" + currentVO.getDisplayDocName() + "---" + currentVO.getRowStatus());
                    //System.out.println("SelectedVO:" + vo.getDisplayDocName() + "---" + vo.getRowStatus());
                    if(currentVO != vo)
                    {
                        currentVO = vo;
                        setEkitCoreDocumentId(currentVO.getDocumentId());
                        setEventTimeTextValue();
                        setExamOrderDescValue();
                    }
                    initNoteTextData();                                       
                }
                
                executeProgressNoteRule(currentVO);
                executeDischgNoteRule(currentVO);
                
                try
                {
                	txtPersonInChargeCode.setText(currentVO.getPersonInCharge());
        			txtPersonInChargeName.setText(DateUtil.getDocName(currentVO.getPersonInCharge()));
                }
                catch (Exception ex)
                {
                    MessageManager.showException(ex.getMessage(), ex);                
                }
            }
        });

        //�f�w�M��ƹ��I���ʧ@
        emrPatListTable.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e)
            {
                //Double Click
                if(e.getClickCount() == 2)
                {
                    int _numOfRows = emrPatListTable.getSelectedRow();
                    int _numOfColumns = emrPatListTable.getSelectedColumn();
                	
                    //���o�f�w�򥻸��
                    loadEmrIpdEncounterData(((EmrIpdEncounterVO) emrPatListTable.getSelectedVO()).getEncounterNo());                      
                    //�]�w���󤺮e
                    txtEncounterNo.setText(GlobalParameters.getInstance().getEmrIpdEncounterVO().getEncounterNo());
                    txtChartNo.setText(GlobalParameters.getInstance().getEmrIpdEncounterVO().getChartNo());
                    txtName.setText(GlobalParameters.getInstance().getEmrIpdEncounterVO().getPatName());
                    txtAge.setText(GlobalParameters.getInstance().getEmrIpdEncounterVO().getAge());
                    txtBirthDate.setText(GlobalParameters.getInstance().getEmrIpdEncounterVO().getBirthDate().toString());
                    txtBedNo.setText(GlobalParameters.getInstance().getEmrIpdEncounterVO().getBedNo());
                    txtDeptName.setText(GlobalParameters.getInstance().getEmrIpdEncounterVO().getDeptName());
                    txtVsName.setText(GlobalParameters.getInstance().getEmrIpdEncounterVO().getVsName());
                    txtAdmitDate.setText(GlobalParameters.getInstance().getEmrIpdEncounterVO().getAdmitDate().toString());
                    txtBranchCode.setText(GlobalParameters.getInstance().getEmrIpdEncounterVO().getBranchCode());
                    txtSex.setText(GlobalParameters.getInstance().getEmrIpdEncounterVO().getSexType().equals("F") ? "�k" : "�k");
                    
                    //�N��Ǹ�
                    GlobalParameters.getInstance().setEncounterNo(GlobalParameters.getInstance().getEmrIpdEncounterVO().getEncounterNo());
                    //�f����
                    GlobalParameters.getInstance().setChartNo(GlobalParameters.getInstance().getEmrIpdEncounterVO().getChartNo());    
                    
//                    JFrame jframe = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, noteMainPanel);                    
//                    String title = "";
//                    title += " �N��Ǹ�:" + GlobalParameters.getInstance().getEmrIpdEncounterVO().getEncounterNo();
//                    title += " �f����:" + GlobalParameters.getInstance().getEmrIpdEncounterVO().getChartNo();
//                    title += " �ɦ�:" + GlobalParameters.getInstance().getEmrIpdEncounterVO().getBedNo();
//                    jframe.setTitle(title);
                    
                    noteFormTitleHM.put("t2", "�N��Ǹ�:" + GlobalParameters.getInstance().getEmrIpdEncounterVO().getEncounterNo());
                    noteFormTitleHM.put("t3", "�f����:" + GlobalParameters.getInstance().getEmrIpdEncounterVO().getChartNo());
                    noteFormTitleHM.put("t4", "�ɦ�:" + GlobalParameters.getInstance().getEmrIpdEncounterVO().getBedNo());
                    setFormTitleHM(noteFormTitleHM);
                    
                    //�B�m�W��´��J:��ܳB�m�ɤ~�|���
                    if("TreNote".equals(filterString))
                    {
                        loadExamOrderDesc();
                    }
                    
                    //20121120 Jeff �f�w�M��DOUBLE CLICK �J�K�������A�ɭ������ӱN���O�۰ʸ����J�K 
                    if(_numOfRows > -1 && _numOfColumns > -1)
                    {
                	String _columnName = emrPatListTable.getColumnName(_numOfColumns);
                	//_columnName = ["�J�K","���{","�X�K"] �~�ݭn�� ��S�w����
                	if(!StringUtil.isBlank(_columnName) && (
                            _columnName.equals(emrPatTableHeaders[6]) || //�J�K
                            _columnName.equals(emrPatTableHeaders[7]) || //���{
                            _columnName.equals(emrPatTableHeaders[8])))	 //�X�K                            
                        {
                            String _noteCategorySelItemValue = "���{�O��"; //default Value:���{�O��
                            if(_columnName.equals(emrPatTableHeaders[6]))
                            { //�J�K
                                _noteCategorySelItemValue = "�J�|�K�n";
                            }
                            else if(_columnName.equals(emrPatTableHeaders[8]))
                            { //�X�K
                                _noteCategorySelItemValue = "�X�|�K�n";
                            }
                            //�f�����O:noteCategoryComboBox�����w������r��
                            //-->�|Ĳ�o��noteCategoryComboBox��ActionListener
                            //System.out.println("" + noteCategoryComboBox.getSelectedItem());
                            noteCategoryComboBox.setSelectedItem(_noteCategorySelItemValue);
                            //System.out.println("" + noteCategoryComboBox.getSelectedItem());
                        }                        
                        else if(!StringUtil.isBlank(_columnName) && _columnName.equals(emrPatTableHeaders[10]))
                        {
                            String strTotalFinish = ((EmrIpdEncounterVO) emrPatListTable.getSelectedVO()).getTotalFinish();
                            String strSignStatus = ((EmrIpdEncounterVO) emrPatListTable.getSelectedVO()).getSignStatus();
                                                        
                            if("����".equalsIgnoreCase(strTotalFinish) && "N".equalsIgnoreCase(strSignStatus))
                            {//�w������ñ��: �ɭ��E�_���@
                                directToDiagPage();
                            }
                            else
                            {//��l�e��                                
                                rollBack();
                            }
                        }                        
                        else
                        {//��l�e��                            
                            rollBack();
                        }
                    }
                    else
                    {//��l�e��                        
                	rollBack();
                    }
                }
            }
        });

        //TabbedPane�����ʧ@
        jideTabbedPane.addChangeListener(new ChangeListener(){
            public void stateChanged(ChangeEvent e)
            {
                int selectedIndex = jideTabbedPane.getSelectedIndex();                
                                
                if(selectedIndex == 1 || selectedIndex == 2)
                {
                    checkChartButton.setVisible(true);
                    directDiagButton.setVisible(true);
                    pedigreeButton.setVisible(true);
                }
                else
                {
                    checkChartButton.setVisible(false);
                    directDiagButton.setVisible(false);
                    pedigreeButton.setVisible(false);
                }                
                                
                //���`����(�s��)
                if(selectedIndex == 2)
                {
                    
                    if(currentVO != null)
                    {
                        String editByCode = "N";
                        FunctionUtil functionUtil = new FunctionUtil();
                        
                        if(currentVO != null && currentVO.getId()>0)
                        {
                            currentVO = getCurrentVO(currentVO.getId());                        
                            editByCode = functionUtil.getEditByCode(currentVO.getId());
                        }                        
                        
                        if("N".equals(editByCode))
                        {
                            changeTextPanel(jideTabbedPane.getSelectedIndex());
                            addBtn.setEnabled(false);
                            delBtn.setEnabled(false);
                        }
                        else
                        {
                            jideTabbedPane.setSelectedIndex(1);
                            String editByName = functionUtil.getEditByName(editByCode);
                            JOptionPane.showMessageDialog(Utils.getParentFrame((JComponent)e.getSource()), "�����f����ƥثe��<"+ editByName +">�s�褤","�����f����ƥثe��<"+ editByName +">�s�褤", JOptionPane.INFORMATION_MESSAGE);
                            
                        }
                    }
                    else
                    {
                        changeTextPanel(jideTabbedPane.getSelectedIndex());
                        addBtn.setEnabled(false);
                        delBtn.setEnabled(false);
                    }
                }
                else
                {                    
                    if(currentVO != null)
                    {
                        FunctionUtil functionUtil = new FunctionUtil();        
                        String editByCode = functionUtil.getEditByCode(currentVO.getId());        
                        if(editByCode.equals(GlobalParameters.getInstance().getSecUserVO().getUsername()))
                        {
                            checkInDocument();
                        }
                    }
                    
                    changeTextPanel(jideTabbedPane.getSelectedIndex());
                    
                    if("OpNote".equals(filterString) && !("012004".equals(GlobalParameters.getInstance().getLoginUserName())))
                    {
                        addBtn.setEnabled(false);
                        delBtn.setEnabled(false);
                    }
                    else
                    {
                        addBtn.setEnabled(true);
                        delBtn.setEnabled(true);
                    }                                    
                }
            }
        });

        //���`�M��ƹ��I���ƥ�
        jList1.addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent e)
            {
                if (e.getValueIsAdjusting())
                {
                    //�I�ﳹ�`�ɱN���`�W����ܦb�s��Ϫ�TITLE
                    outsideNoteEkitPanel.setBorder(BorderFactory.createTitledBorder(jList1.getSelectedValue().toString()));
                    
                    syncTextPaneWithSourcePane();
                    
                    //update by leon 2012-07-10:���`��������ץ�
                    fixDocumentText();
                    hm.put(oldSeledtedItem, noteEkitPanel.getEkitPanel().getEkitCore().getDocumentBody());
                    
                    String _selectedValue = (String)jList1.getSelectedValue();
                                        
                    //update by leon 2014-08-25: FOR �X�K�_��,�X�|�E�_���`�ޱ���Ū
                    setDischgDiagReadOnly();                    
                    
                    if(!StringUtil.isBlank(hm.get(_selectedValue)))
                    {                        
                    	
                    	noteEkitPanel.getEkitPanel().getEkitCore().getTextPane().setText(hm.get(_selectedValue).toString());
                    }
                    else
                    {
                        //update by leon 2012-05-09:�ץ��Y�|����J���󤺮e�Y�ϥίf�w��ƥ\��δ��J�Ϥ��L�k���`��ܤ��e
                        noteEkitPanel.getEkitPanel().getEkitCore().getTextPane().setText("<p></p>");
                    }
                    
                    syncTextPaneWithSourcePane();
                    
                    oldSeledtedItem = jList1.getSelectedValue().toString();
                    oldSeledtedIndex = jList1.getSelectedIndex();
                    
                    //�̯f�����OIDŪ���ۭq�f�w�ܼƸ��
                    EmrNoteCategoryVO vo = (EmrNoteCategoryVO) ((HCComboBoxModel) noteCategoryComboBox.getModel()).getVO();
                    noteDataObjectPanel.loadDataobjectData(vo.getId().toString(), oldSeledtedItem);
                    noteDataObjectPanel.setNoteCategory(filterString);
                }
            }
        });

        //�bekit�[�Jkey��ť��
        noteEkitPanel.getEkitPanel().getEkitCore().getTextPane().addKeyListener(new KeyAdapter(){            
            public void keyPressed(KeyEvent e)
            {
                //��ťctrl + P���ʧ@
                if(e.getKeyCode() == KeyEvent.VK_P)
                {
                    if(e.isControlDown())
                    {
                        if(jideTabbedPane.getSelectedIndex() == 2)
                        {
                            String phraseType = chapterHm.get(jList1.getSelectedValue().toString()) != null ? chapterHm.get(jList1.getSelectedValue().toString()).toString() : "";
                            //new NotePhraseDialog(noteEkitPanel.getEkitPanel().getEkitCore().getTextPane(), phraseType);
                            new NotePhraseDialog(noteEkitPanel, phraseType);
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(Utils.getParentFrame((JComponent)e.getSource()), "�Х������ܯf���s�説�A");
                        }
                    }
                }
                
                //update by leon 2012-05-28:�]�w���w�鷺�e�wMD5���覡�i��ק�P�_�A�G���b�ݭn���q��ť�޿�
                //��ť�ק�ʧ@
                /*
                if("R".equals(currentVO.getRowStatus()) && !ignoreKeyList.contains(e.getKeyCode()))
                {
                    System.out.println("EkitPane value change");
                    currentVO.setRowStatus("U");
                } 
                */
                
                //update by leon 2012-05-29:�ֱ���ϥΦۭq�K�W
                if(e.getKeyCode() == KeyEvent.VK_V)
                {
                    String _selectedValue = (String)jList1.getSelectedValue();
                    String _meanData = (String)dischgNoteComboBox.getSelectedItem();
                    _meanData = _meanData.split(":")[0];
                    
                    //System.out.println("_selectedValue: " + _selectedValue);
                    //System.out.println("_meanData: " + _meanData);
                    
                    if(!(("02.�X�|�E�_".equals(_selectedValue)) && ("D".equals(_meanData) || "C".equals(_meanData))))
                    {
                        if(e.isControlDown())
                        {                    
                            noteEkitPanel.getEkitPanel().getEkitCore().jbtnPaste.doClick();
                            e.consume();
                        }
                    }
                }
                                                                        
                //update by leon 2012-05-29:�ֱ���ϥΤW�@�B
                if(e.getKeyCode() == KeyEvent.VK_Z)
                {
                    if(e.isControlDown())
                    {
                        noteEkitPanel.getEkitPanel().getEkitCore().jbtnUndo.doClick();
                        e.consume();
                    }
                }
                
                //update by leon 2012-05-29:�ֱ���ϥΤU�@�B
                if(e.getKeyCode() == KeyEvent.VK_Y)
                {
                    if(e.isControlDown())
                    {
                        noteEkitPanel.getEkitPanel().getEkitCore().jbtnRedo.doClick();
                        e.consume();
                    }
                }
                
                //update by leon 2012-05-29:
                /*
                if(e.getKeyCode() == KeyEvent.VK_B)
                {
                    if(e.isControlDown())
                    {                    
                        noteEkitPanel.getEkitPanel().getEkitCore().jbtnBold.doClick();
                        e.consume();
                    }
                }
                */
                
                //update by leon 2012-05-29:
                /*
                if(e.getKeyCode() == KeyEvent.VK_I)
                {
                    if(e.isControlDown())
                    {                    
                        noteEkitPanel.getEkitPanel().getEkitCore().jbtnItalic.doClick();
                        e.consume();
                    }
                }
                */
                
                //update by leon 2012-05-29:
                /*
                if(e.getKeyCode() == KeyEvent.VK_U)
                {
                    if(e.isControlDown())
                    {                    
                        noteEkitPanel.getEkitPanel().getEkitCore().jbtnUnderline.doClick();
                        e.consume();
                    }
                }
                */
            }
        });
        
        //Weekly summary JComboBox Action Event
        wkSummaryComboBox.addActionListener(new ActionListener()
        {
        	public void actionPerformed(ActionEvent e)
		{
                    //���o�U�Կ��ҿ�ܪ���
                    String _meanData = (String)wkSummaryComboBox.getSelectedItem();
        		
                    //�Y�����ʭ�
                    if(currentVO != null)
                    {                	                	
                	if(!_meanData.equalsIgnoreCase(currentVO.getReportThirdTypeScid()))
                	{                		
                            if(currentVO.getCreatedBy().equals("012004-1"))
                            {
                                showMessage("�t�Ψ̳W�d�۰ʫإߤ����n�f���A���o�ܧ�f���l���O");                                
                                wkSummaryComboBox.setSelectedItem(currentVO.getReportThirdTypeScid());                                
                            }
                            else
                            {
                                currentVO.setReportThirdTypeScid(_meanData);
                                if(currentVO.getId() > 0)
                                {
                                    currentVO.setRowStatus("U");                                    
                                }
                                
                                //�Y��ܬ�Transfer Note��On Service Note�u�X���M��ѨϥΪ̿��
                                if("Transfer Note".equals(_meanData) || "On Service Note".equals(_meanData))
                                {
                                    GetTranBedInfoDialog getTranBedInfoDialog = new GetTranBedInfoDialog("progressnote", _meanData, getInstance());
                                }
                            }
                	}                	                	
                    }        		        	
		}
        });
        
        //update by leon 2014-02-19:�X�K����l���O,�U�Կ�沧�ʧ�sVO��������
        dischgNoteComboBox.addActionListener(new ActionListener()
        {        	
            public void actionPerformed(ActionEvent e)
            {                
                String _meanData = (String)dischgNoteComboBox.getSelectedItem();
            	_meanData = _meanData.split(":")[0];
            	                            
		if(currentVO != null)
                {
                    //�ˬdreportThirdTypeScid��즳�L����
                    if(!_meanData.equalsIgnoreCase(currentVO.getReportThirdTypeScid()))
                    {
                        if(currentVO.getCreatedBy().equals("012004-1"))
                        {
                            showMessage("�t�Ψ̳W�d�۰ʫإߤ����n�f���A���o�ܧ�f���l���O");                                
                            
                            if("D".equals(currentVO.getReportThirdTypeScid()))
                            {
                                dischgNoteComboBox.setSelectedItem("D:�X�|�K�n");
                            }
                            else if("T".equals(currentVO.getReportThirdTypeScid()))
                            {
                                dischgNoteComboBox.setSelectedItem("T:���K�n");
                            }
                            else if("C".equals(currentVO.getReportThirdTypeScid()))
                            {
                                dischgNoteComboBox.setSelectedItem("C:���q���M�K�n");
                            }                                                    
                        }
                        else
                        {
                            //�]�wreportThirdTypeScid���
                            currentVO.setReportThirdTypeScid(_meanData);
                            if(currentVO.getId() > 0)
                            {//�D�s�W->rowStatus = U
                                //update by leon 2014-05-26: �ݭץ��ܧ�j���O����ܤl���O�]�|Ĳ�o
                                //currentVO.setRowStatus("U");
                                System.out.println("6");
                            }
                            
                            if("T".equals(_meanData))
                            {
                                    GetTranBedInfoDialog getTranBedInfoDialog = new GetTranBedInfoDialog("dischgnote", _meanData, getInstance());
                            }
                        }                                                
                    }
                }                                                             
            }
        });
    }

    /**
     * �]�w�u��C���s��ť��
     * @see
     */
    public void setToolBarButtonAction() {
        //�s�W���s�ʧ@
        addBtn.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
            	setDischgDiagReadOnly();
            	
            	Connection conn = null;
                try
                {
                    boolean flag = checkData();

                    if(flag)
                    {
                    	return;
                    }                                       
                    
                    //�إ߷s�W���
                    conn = DBConnectionHelper.getInstance().getConnection(
                    		GlobalParameters.getInstance().getMySql_url(), 
                    		GlobalParameters.getInstance().getMySql_usrid(), 
                    		GlobalParameters.getInstance().getMySql_pwd());
                    MySeqGenerator sg = new MySeqGenerator(conn);
                    
                    EmrPatNoteVO newVO = new EmrPatNoteVO();
                    newVO.setEventTime(new Date(new java.util.Date().getTime()));
                    newVO.setChartNo(GlobalParameters.getInstance().getChartNo());
                    newVO.setEncounterNo(GlobalParameters.getInstance().getEncounterNo());
                    newVO.setDocumentFormat("HTML");
                    newVO.setDocumentDesc(noteCategoryComboBox.getSelectedItem().toString());
                    newVO.setDocumentId(sg.genSeq("DocumentId"));
                    newVO.setEventId(sg.genSeq("Event"));
                    newVO.setDocumentSystemScid("DOC");
                    String docName = DateUtil.getDocName(GlobalParameters.getInstance().getLoginUserName());
                    
                    //�s�W�f����,�Y�n�J�̬��D�v��v,��ñ����v�Y���n�J��
                    if("VS".equals(GlobalParameters.getInstance().getDocType()))
                    {
                    	newVO.setAdviserByScid(GlobalParameters.getInstance().getLoginUserName());
                    	newVO.setAdviserByName(docName);
                        newVO.setPersonInCharge(GlobalParameters.getInstance().getLoginUserName());
                    }
                    //�n�J�̫D�D�v��v��
                    else
                    {
                    	String strBranchCode = GlobalParameters.getInstance().getEmrIpdEncounterVO().getBranchCode();
                        //�Y��ICU��ñ����v����Z��v
                    	if("A031".equals(strBranchCode) || "A032".equals(strBranchCode) || "A152".equals(strBranchCode))
                    	{
                            //���o��Z��v�A�Y���]�w����D�v��v
                            HashMap dutyDocInfo = getDutyDocInfo(GlobalParameters.getInstance().getEncounterNo());
                                        	
                            newVO.setAdviserByScid(dutyDocInfo.get("doc_code").toString());
                            newVO.setAdviserByName(dutyDocInfo.get("doc_name").toString());
                            newVO.setPersonInCharge(dutyDocInfo.get("doc_code").toString());
                    	}
                    	//�DICU��ñ����v����D�v��v
                    	else
                    	{
                            newVO.setAdviserByScid(GlobalParameters.getInstance().getEmrIpdEncounterVO().getVsCode());
                            newVO.setAdviserByName(GlobalParameters.getInstance().getEmrIpdEncounterVO().getVsName());
                            newVO.setPersonInCharge(GlobalParameters.getInstance().getEmrIpdEncounterVO().getVsCode());
                    	}                    	
                    }
                    
                    newVO.setEnteredByScid(GlobalParameters.getInstance().getLoginUserName());
                    newVO.setEnteredByName(docName);
                    newVO.setPointOfCareTerminalScid(getIPAddress());
                    newVO.setId(0L);
                    newVO.setDateCreated(new java.util.Date());
                    newVO.setCreatedBy(GlobalParameters.getInstance().getLoginUserName());
                    newVO.setRowStatus("C");
                    newVO.setDisplayName(newVO.getDisplayDocName());
                    
                    if(GlobalParameters.getInstance().getEmrIpdEncounterVO() != null)
                    {
                        newVO.setExamDate(GlobalParameters.getInstance().getEmrIpdEncounterVO().getAdmitDate());
                    }
                    
                    newVO.setExamOrderDesc(noteCategoryComboBox.getSelectedItem().toString());                       
                                        
                    setDefaultData(newVO);
                    
                    if("ProgressNote".equals(filterString))
                    {
                        //Weekly summary �ﶵ�ιw�]��:Progress Note
                        String _newWkSummaryData = wkSummaryComboBoxShowData[0];
                        
                        //�]�wreportThirdTypeScid���
                        newVO.setReportThirdTypeScid(_newWkSummaryData);
                        //�]�w�U�Կﶵ
            		//wkSummaryComboBox.setSelectedItem(newVO.getReportThirdTypeScid());
                    }
                    
                    //update by leon 2014-02-19:�X�K�s�W��,�w�]�l���O��D:�X�|�K�n
                    if("DischgNote".equals(filterString))
                    {
                        String _newDischgNoteData = dischgNoteComboBoxShowData[0]; //D:�X�|�K�n
                        newVO.setReportThirdTypeScid(_newDischgNoteData.split(":")[0]); //D                      
                    }
                    
                    //�w�]�����ݭnVS COMMENT,�Y�n�J�̫DD1�D�v��v�]�w���ݭnVS COMMENT
                    String commNeedFlag = "N";
                    if(!"D1".equals(GlobalParameters.getInstance().getSecUserVO().getUserGroup()))
                    {
                    	commNeedFlag = "Y";
            		}
                    newVO.setCommNeedFlag(commNeedFlag);
                    
                    //��s�e��
                    emrPatNoteTable.addVO(0, newVO);
                    //emrPatNoteTable.setRowSelectionInterval(emrPatNoteTable.getRowCount() - 1, emrPatNoteTable.getRowCount() - 1);
                    currentVO = newVO;
                    setEkitCoreDocumentId(currentVO.getDocumentId());
                    setEventTimeTextValue();
                    setExamOrderDescValue();

                    initNoteTextData();

                    //�������`����
                    jideTabbedPane.setSelectedIndex(2);
                    
                } catch (Exception ex) {
                    MessageManager.showException(ex.getMessage(), ex);
                    logger.debug(ex.getMessage(), ex);
                } finally {
                    DBConnectionHelper.getInstance().cleanConnection(conn);
                }
            }
        });
    
        //�R�����s�ʧ@
        delBtn.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e)
        {        
        	try
        	{
        		if(currentVO != null)
        		{
        			if(currentVO.getReportStatusScid().equals("2"))
        			{
        				JOptionPane.showMessageDialog(null,"�w������ƵL�k�R��!");
        				return;
        			}
        			
        			if("012004-1".equals(currentVO.getCreatedBy()))
        			{
        				JOptionPane.showMessageDialog(null,"�t�Ψ̳W�d�۰ʫإߤ����n�f���A���o�R��");
        				return;
        			}
               
        			int op = JOptionPane.showConfirmDialog(null,"�O�_�R���ӵ����?","Info",JOptionPane.OK_CANCEL_OPTION);
        			if(op == JOptionPane.YES_OPTION)
        			{
        				setDischgDiagReadOnly();
        				
        				if(!currentVO.getRowStatus().equals("C"))
        				{
        					//delete emrpatnote data
        					currentVO.setRowStatus("D");
        					ArrayList list = new ArrayList();
        					list.add(currentVO);
        					EmrPatNoteDAO.getInstance().updateData(list);
        					//�q�l�f��        					
        					if(getRptdbFlag())
        					{
        						doEmrSheet("D");
        					}
        					        					
        					rollBack();
        				}
        				else
        				{
        					rollBack();
        				}
        				//JOptionPane.showMessageDialog(null, "�R�����\!");   
        			}
        		}
        	}
        	catch (Exception ex)
        	{
        		MessageManager.showException(ex.getMessage(),ex);
        		logger.debug(ex.getMessage(),ex);
        	}
        }});

        //�Ȧs���s�ʧ@
        tmpBtn.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e)
        {            
            tempSave();
        }});

        //�s�ɫ��s�ʧ@
        savBtn.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e)
        {
            if(currentVO != null)
            {
                try
        	{                    
                    //�s�ɫe�]��
                    if(limitsOfSave(true) == false){return;}

                    //�]�ָ�ƬO�_������
                    checkData2();                    	                    
                    
                    //�ץ�HTML�ƪ��r��
                    fixDocumentText();
                    
                    //�s�ɭץ�rowStatus
                    if("R".equals(currentVO.getRowStatus())){currentVO.setRowStatus("U");}
                    
                    //�s��
                    saveData("2");
                    setDischgDiagReadOnly();
                    rollBack();
                }
        	catch(Exception ex)
        	{
                    ex.printStackTrace();
                    logger.debug(ex.getMessage(), ex);
        	}
            }
        }});
    
        //�_��,���s�ɫ��s�ʧ@
        rbkBtn.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e)
        	{
        		setDischgDiagReadOnly();        		
        		rollBack();
        	}
        });
    
        //���y���s�ʧ@  
        phraseButton.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
            if(jideTabbedPane.getSelectedIndex() == 2){
              String phraseType = chapterHm.get(jList1.getSelectedValue().toString())!=null ? chapterHm.get(jList1.getSelectedValue().toString()).toString() : "";
              //System.out.println("phraseType: " + phraseType);
              new NotePhraseDialog(noteEkitPanel,phraseType);
            }else
              JOptionPane.showMessageDialog(Utils.getParentFrame((JComponent)e.getSource()), "�Х������ܯf���s�説�A");
          }
        });
              
        //�q�f�]��
        checkChartButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {                                      
                try
                {
                    EmrPatNoteErrlogDialog emrPatNoteErrlogDialog = new EmrPatNoteErrlogDialog(GlobalParameters.getInstance().getEncounterNo());
                }
                catch (Exception ex)
                {
                    MessageManager.showException(ex.getMessage(), ex);
                    logger.debug(ex.getMessage(), ex);
                }
            }
        });
        
        //�d����v���
        btnPersonInCharge.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {            	            	   
                try
                {
                	
                	GetDocInfoDialog getDocInfoDialog = new GetDocInfoDialog(getInstance(), eventTimeTextField.getText());
                }
                catch (Exception ex)
                {
                    MessageManager.showException(ex.getMessage(), ex);
                    logger.debug(ex.getMessage(), ex);
                }
            }
        });
        
        //�w�����s�ʧ@  
        previewPrintButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if(!currentVO.getRowStatus().equals("R"))
                {
                    JOptionPane.showMessageDialog(null, "��ƥ��s��,�L�k�w��", "Info", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                
                if(currentVO.getReportStatusScid().equals("T"))
                {
                    JOptionPane.showMessageDialog(null, "��ƥ��s��, �L�k�w��", "Info",
                    		JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
    
                try
                {
                    /*�N�f�������r��,�s�覨�C�L�r��*/
                    ArrayList list = new ArrayList();
                    JTextPane textPane = new JTextPane();
                    textPane.setContentType("text/html");
                    
                    //�̳��`
                    Iterator it = chapterOrderArray.iterator();
                    while(it.hasNext())
                    {
                        String key = it.next().toString();
                        //���o�ӳ��`�s�誺���e
                        String content = String.valueOf(hm.get(key));                                    
                        
                        //�ˮָӳ��`���e�O�_���šATRUE�G�šAFALSE�G�D��
                        String temp = content;
                                            
                        boolean flag;
                        if(temp.indexOf("<img",0) != -1)
                        {
                            flag = false;    
                        }
                        else
                        {
                            int stIdx = 0;
                            while((stIdx = temp.indexOf("<",0)) != -1)
                            {
                                int edIdx = temp.indexOf(">",stIdx);
                                temp = temp.substring(0,stIdx) + temp.substring(edIdx+1);
                            }
                            
                            //�h���ť�
                            temp = temp.replaceAll(" ","");
                            temp = temp.replaceAll("&#160;","");
                            temp = temp.replaceAll("&nbsp;","");
                            
                            //�h������
                            temp = temp.replaceAll("<br>","");
                            temp = temp.replaceAll("\n","");
                            temp = temp.replaceAll(String.valueOf((char)10),"");                                    
                                                                    
                            if(temp.length()==0)
                            {
                                flag = true;
                            }
                            else
                            {
                                flag = false;
                            }    
                        }
                                                                            
                        //�p�G�S�����e,�s���`�]���L                                    
                        if (content != null && content.trim().length() > 0 && (flag==false))
                        {
                            textPane.setText("<B>" + key + "</B>" + hm.get(key).toString());                                                                    
                            list.add(textPane.getText());
                        }
                    }
                    
                    
                    ReportUtil reportUtil = new ReportUtil();
                    reportUtil.setEmrPatNoteVO(currentVO);
                    reportUtil.setChapterDataMap(hm);
                    reportUtil.setChapterList(emrNoteChapterData);                                       
                    
                    ArrayList printDataList = reportUtil.packagePrintData();
                    
                    /**
                     * �C�L�ʧ@
                     * ��N���i�L��|�D�ɡA�G����EmrIpdEncounterVo
                     */
                    BaseReport report = null;
                    if(GlobalParameters.getInstance().getEmrIpdEncounterVO() == null)
                    {
                        if("OpNote".equals(filterString))
                        {
                            report =  new OrReport(currentVO.getSheetXmlMeta());
                        }
                    }
                    else
                    {
                        if("DischgNote".equals(filterString))
                        {
                            //report = new DisChgReport();                        	
                            //report = new DisChgReport(currentVO.getReportThirdTypeScid(),currentVO.getPersonInCharge(),true);
                            report = new DisChgReport(currentVO,false);
                        }
                        else if("ChtDischgNote".equals(filterString))
                        {
                            //report = new DisChgReport();
                            report = new ChtDisChgReport(currentVO,false);
                        }
                        else if("AdminNote".equals(filterString))
                        {
                            //report = new AdminNoteReport();
                            report = new AdminNoteReport(currentVO,false);
                        }
                        else if("ProgressNote".equals(filterString))
                        {
                            //report = new ProgressNoteReport(0);
                        	report = new ProgressNoteReport(0,currentVO,false);
                        }
                        else if("OpNote".equals(filterString))
                        {
                            report =  new OrReport(currentVO.getSheetXmlMeta());                            
                        }
                        else if("TreNote".equals(filterString))
                        {
                            report = new TreNoteReport(currentVO.getExamOrderDesc());
                        }
                    }
                    
                    if ("OpNote".equals(filterString) && "N".equals(((OrReport) report).getOrVo().feeOpFlag)) {
                        JOptionPane.showMessageDialog(null, "��N������|�������A�Ч���������A�C�L!");
                        //return;
                    }
                    
                    if (report != null) {
                    	//report.print(GlobalParameters.getInstance().getEmrIpdEncounterVO());
                        report.print(printDataList, GlobalParameters.getInstance().getEmrIpdEncounterVO(), null, BaseReport.PRINT_TYPE_PREVIEW, null);                        
                    }
                } catch (Exception ex) {
                    MessageManager.showException(ex.getMessage(), ex);
                    logger.debug(ex.getMessage(), ex);
                }
            }
        });
    
        //�C�L���s�ʧ@
        printButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                //�ˮ֥D�����W�ҿ�ܤ��H���f���M��,���A����R(�߿W)�~�i����C�L�\��
                if(!currentVO.getRowStatus().equals("R"))
                {
                    JOptionPane.showMessageDialog(null, "��ƥ��s��, �L�k�C�L", "Info",
                    		JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                
                if(currentVO.getReportStatusScid().equals("T"))
                {
                    JOptionPane.showMessageDialog(null, "��ƥ��s��, �L�k�C�L", "Info",
                    		JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                
                
                try
                {
                    String noteType = filterString; //�f�Һ��@���O(�X�K�B�J�K�B���{...)
                    String branchCode = null; //���w�C�L�Ӽh
                    String groupCode = null; //���w�C�L�L���
                    String copies = "1"; //�C�L����
                    String parms = ""; //�]�t�_�l���,�C�L���O,�PDOCUMENT ID
                    
                    //�Y�����{�O��
                    if("ProgressNote".equals(filterString))
                    {
                        //���X�C�L����
                        ProgressNotePrintDialog progressNotePrintDialog = new ProgressNotePrintDialog(
                        		GlobalParameters.getInstance().getEmrIpdEncounterVO().getBranchCode());
                        
                        //���o�C�L�������ҳ]�w�������ݩ�
                        ArrayList rs = progressNotePrintDialog.selectedVOs;
                        branchCode = progressNotePrintDialog.branchCode;
                        groupCode = progressNotePrintDialog.groupCode;
                        copies = progressNotePrintDialog.copies;
                        
                        //�Y�]�w���_�l��Ƥ���0,noteType���["_MPtray"
                        if(!("0".equals(progressNotePrintDialog.lineNo)))
                        {
                            noteType += "_MPtray";
                        }
                  
                        //�]�wparms
                        parms += "lineNo:" + progressNotePrintDialog.lineNo + ";";
                        //update by leon 2012-04-10:���{�O�����C�L,�W�[�C�L�ťճ檺�\��
                        parms += "printType:" + progressNotePrintDialog.printType + ";";
                
                        for(int i=0;i<rs.size();i++)
                        {
                            EmrPatNoteVO vo = (EmrPatNoteVO)rs.get(i);
                            parms += "DocumentId" + i + ":" + vo.getDocumentId() + ";";
                        }
                    }
                    //�Y����N
                    else if("OpNote".equals(filterString))
                    {
                        //�ˮ֤�N�O���槹���_
                        OrReport report = new OrReport(currentVO.getSheetXmlMeta());
                        if ("N".equals(report.getOrVo().feeOpFlag)) {
                            JOptionPane.showMessageDialog(null, "��N������|�������A�Ч���������A�C�L!");
                            return;
                        }
                        
                        PrinterLocationDialog printerLocationDialog = new PrinterLocationDialog("OR");
                        branchCode = printerLocationDialog.branchCode;
                        groupCode = printerLocationDialog.groupCode;
                        copies = printerLocationDialog.copies;
                        
                    }
                    //��L
                    else
                    {
                        PrinterLocationDialog printerLocationDialog = new PrinterLocationDialog(
                        		GlobalParameters.getInstance().getEmrIpdEncounterVO().getBranchCode());
                        branchCode = printerLocationDialog.branchCode;
                        groupCode = printerLocationDialog.groupCode;
                        copies = printerLocationDialog.copies;
                    }
    
                    //call �C�Lwebservice
                    if(branchCode!=null)
                    {
                        logger.debug("==�C�L�Ѽ�==");
                        logger.debug("NoteType:" + noteType);
                        logger.debug("branchCode:" + branchCode);
                        logger.debug("groupCode:" + groupCode);
                        logger.debug("copies:" + copies);
                        logger.debug("DocumentId:" + currentVO.getDocumentId());
                        logger.debug("EncounterNo:" + GlobalParameters.getInstance().getEncounterNo());
                        logger.debug("Parameters:" + parms);
                        logger.debug("�C�L�}�l...");
                        
                        System.out.println("==�C�L�Ѽ�==");
                        System.out.println("NoteType:" + noteType);
                        System.out.println("branchCode:" + branchCode);
                        System.out.println("groupCode:" + groupCode);
                        System.out.println("copies:" + copies);
                        System.out.println("DocumentId:" + currentVO.getDocumentId());
                        System.out.println("EncounterNo:" + GlobalParameters.getInstance().getEncounterNo());
                        System.out.println("Parameters:" + parms);
                        System.out.println("�C�L�}�l...");
                
                        //update by leon 2012-06-08:�C�L�ɨϥ�THREAD����                        
                        new PrintThread(noteType,branchCode,currentVO.getDocumentId(),GlobalParameters.getInstance().getEncounterNo(),parms,copies,GlobalParameters.getInstance().getChartNo(),groupCode,currentVO.getReportThirdTypeScid()).start();
                        //����
                        //new PrintThread(noteType,branchCode,currentVO.getDocumentId(),GlobalParameters.getInstance().getEncounterNo(),parms,copies,GlobalParameters.getInstance().getChartNo(), null,currentVO.getReportThirdTypeScid()).start();
                    }
                }
                catch (Exception ex)
                {
                    MessageManager.showException(ex.getMessage(),ex);
                    logger.debug(ex.getMessage(),ex);
                }       
            }
        });
    
        //ditto���s�ʧ@
        dittoButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                /**�Y��ܳ��`���޽d��*/
                if(jideTabbedPane.getSelectedIndex() == 2)
                {
                    /**�Y��ܥX�K�ίf�K*/
                    if(filterString.equals("DischgNote") || filterString.equals("ChtDischgNote") || filterString.equals("AdminNote"))
                    {
                        new DittoDialog(DittoDialog.TYPE1, filterString, emrNoteChapterData, getInstance());
                    }
                    /**�Y��ܾ��{�O��*/
                    else if(filterString.equals("ProgressNote"))
                    {
                        new DittoDialog(DittoDialog.TYPE2, filterString, emrNoteChapterData, getInstance());                        
                    }
                }
                /**�Y��ܥH���f���M��*/
                else
                {
                    JOptionPane.showMessageDialog(Utils.getParentFrame((JComponent)e.getSource()), "�Х������ܯf���s�説�A");
                }          
          }
        });
    
        //�w�]�d�����s�ʧ@
        defaultValueButton.addActionListener(new ActionListener(){
          public void actionPerformed(ActionEvent e){
            try
            {
                if(jideTabbedPane.getSelectedIndex() != 2){
                    JOptionPane.showMessageDialog(Utils.getParentFrame((JComponent)e.getSource()), "�Х������ܯf���s�説�A");
                    return;
                }
            
                //update by leon 2012-03-27:�����s�W�X�|�K�n�ɪ��w�]�d��,�אּ��USER�i��ܦ�ɫ��U�w�]�ȫ��s�Ӭd��
                //if(filterString.equals("DischgNote") ||filterString.equals("ChtDischgNote") )
                if(filterString.equals("DischgNote"))
                {
                    setDefaultDischgNoteData();    
                    noteEkitPanel.setDataHasChangedStatus(true);
                }
                else
                {
                    //���o�w�]��WS
                    DocDataObjectStub stub = new DocDataObjectStub();
                    stub.setEndpoint(GlobalParameters.getInstance().getDocServerURL());
                    notews.ResultDTO dto = stub.getNoteDefaultValue(
                    		GlobalParameters.getInstance().getEncounterNo(),
                    		GlobalParameters.getInstance().getChartNo(),
                    		filterString,
                    		"");
                    
                    if(dto.getResult()!=null && dto.getResult().length()>0){
                      //System.out.println(dto.getResult());
                      //�Nxml�sĶ
                      Document document = DocumentHelper.parseText(dto.getResult());            
                      Element rootElement = document.getRootElement();
                      //�B�z���
                      HashMap rsHm = new HashMap();
                      for ( int i = 0, size = rootElement.nodeCount(); i < size; i++ ) {
                        Node node = rootElement.node(i);
                        if(node instanceof Element){
                          Element cNode = (Element)node;
                          
                          String noteType = cNode.attribute("noteType").getValue();
                          String content = SimpleXMLParser.escapeXML(cNode.getText(), false);
                          content = StringUtil.isBlank(content) ? "" : "<p>" + content + "</p>";
                            
                          //System.out.println("content:" + StringUtil.fixInP(content));
                          rsHm.put(noteType, StringUtil.fixInP(content));
                        }
                      }
                      //�]�w�e��
                      setNoteTemplate(rsHm,true);
                      noteEkitPanel.setDataHasChangedStatus(true);
                    }
                }
            }
            catch (Exception ex)
            {
              MessageManager.showException(ex.getMessage(),ex);
              logger.debug(ex.getMessage(),ex);
            }
          }
        });
        
        //Log
        logButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try
                {
                    noteEkitPanel.getEkitPanel().getEkitCore().setDocumentText(GlobalParameters.getInstance().getLogMsg());
                }
                catch (Exception ex)
                {
                    MessageManager.showException(ex.getMessage(),ex);
                    logger.debug(ex.getMessage(),ex);
                }
            }
        });
        
        //�j��s��
        fourceEditButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){            	            
                try
                {
                    checkInDocument();
                    jideTabbedPane.setSelectedIndex(2);
                }
                catch (Exception ex)
                {
                    MessageManager.showException(ex.getMessage(),ex);                    
                }
            }
        });
        
        //�������
        diffPdfButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){                             
                try
                {                    
                    doDiffPdf();
                }
                catch (Exception ex)
                {
                    MessageManager.showException(ex.getMessage(),ex);                    
                }
            }
        });
         
        //���{�`ñ
        progressAllButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){            	            
                try
                {
                	if(getRptdbFlag())
                	{
                		doEmrSheet("Batch");
                	}                	                	
                }
                catch (Exception ex)
                {
                    MessageManager.showException(ex.getMessage(),ex);                    
                }
            }
        });
        
        //�E�_���@
        directDiagButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){                             
                try
                {
                    directToDiagPage();
                }
                catch (Exception ex)
                {
                    MessageManager.showException(ex.getMessage(),ex);                    
                }
            }
        });      
        
        //�ɰeñ��(�|)
        hcaEmrButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    if(getRptdbFlag())
                    {
                        /*
                        //�妸�ɵo
                        Statement stmt = null;
                        ResultSet rs = null;
                        Connection conn;        
                        
                        try
                        {               
                            conn = DBConnectionHelper.getInstance().getMySqlConnection();
                            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                                            
                            StringBuffer sql = new StringBuffer();
                            sql.append(" SELECT DISTINCT a.document_id FROM emr.emr_pat_note_log a WHERE ");
                            sql.append(" a.last_updated >= TO_date('2019-01-02 08:00', 'yyyy-mm-dd hh24:mi') ");
                            sql.append(" AND a.last_updated < TO_date('2019-01-02 13:41', 'yyyy-mm-dd hh24:mi') ");
                            sql.append(" AND a.report_subtype_scid IN('adminnote', 'progressnote', 'dischgnote', 'opnote') ");
                            //sql.append(" AND a.document_id = '20190102001355' ");
                                
                            rs = stmt.executeQuery(sql.toString());            
                            if(rs.next())
                            {
                                String sql1 = "SELECT * FROM emr.emr_pat_note a WHERE a.document_id = '"+ rs.getString("document_id") +"'";                                
                                ArrayList list1 = EmrPatNoteDAO.getInstance().findAllBySql(sql1);
                                if (list1.size() > 0)
                                {
                                    for (int j = 0; j < list1.size(); j++)
                                    {                                
                                        currentVO = (EmrPatNoteVO) list1.get(j);
                                        filterString = "ProgressNote";                                
                                        loadEmrIpdEncounterData(currentVO.getEncounterNo());
                                        
                                        doEmrSheet("C");
                                    }
                                }
                            }
                        }
                        catch(Exception ee)
                        {
                            MessageManager.showException(ee.getMessage(),ee);
                        }
                        finally
                        {
                            DBConnectionHelper.getInstance().cleanRsAndStmt(rs,stmt);
                        }
                        */
                                                                                                        
                        //�浧�ɵo
                        doEmrSheet("C");
                    }
                }
                catch (Exception ex)
                {
                    MessageManager.showException(ex.getMessage(),ex);
                    logger.debug(ex.getMessage(),ex);
                }
            }
        });
        
        //�ɰeñ��(��)
        hcaEmrOpButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                try
                {                                                    
                    //��N���i�ɵo
                    batchReDoOpNoteEmrSheet();                                    
                }
                catch (Exception ex)
                {
                    MessageManager.showException(ex.getMessage(),ex);
                    logger.debug(ex.getMessage(),ex);
                }
            }
        });
        
        //�a�ھ�
        pedigreeButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                try
                {                    
                    StringBuffer strUrl = new StringBuffer();
                    //strUrl.append("rundll32 url.dll,FileProtocolHandler ");
                    strUrl.append(GlobalParameters.getInstance().getFamilyTreeUrl());                    
                    strUrl.append(("?chHospArea=" + "XD")); //�|�� �ὬHL�B�x��TC�B�j�LDL�B�x�_TP
                    strUrl.append(("&chOCaseNo=" + GlobalParameters.getInstance().getEncounterNo())); //��|��
                    strUrl.append(("&chMrNo=" + GlobalParameters.getInstance().getChartNo())); //�f����
                    strUrl.append(("&chSaveDT=" + "1070820000000")); //�����ɶ�(�Pı�Sԣ�@�� �ڳ���T�w��)                            
                    //System.out.println(strUrl.toString());
                    //Runtime.getRuntime().exec(strUrl.toString());
                    Runtime.getRuntime().exec(new String[] {"C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe", strUrl.toString()});
                }
                catch (Exception ex)
                {
                    MessageManager.showException(ex.getMessage(),ex);
                    logger.debug(ex.getMessage(),ex);
                }
            }
            
            /*
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    new EmrFamilyTreeDialog(noteEkitPanel);                    
                }
                catch (Exception ex)
                {
                    MessageManager.showException(ex.getMessage(),ex);
                    logger.debug(ex.getMessage(),ex);
                }
            }
            */
        });
              
        //�D�v��vTextField
        vsCodeField.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    loadEmrPatList();
                }
            }
        });
        
        //----------�d�߭����\��s Start----------
        //�d��
        queryButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try
                {
                    loadEmrPatList();
                }
                catch (Exception ex)
                {
                    MessageManager.showException(ex.getMessage(),ex);
                    logger.debug(ex.getMessage(),ex);
                }
            }
        });
        
      queryButton1.addActionListener(new ActionListener(){
          public void actionPerformed(ActionEvent e){
              try
              {
                  loadEmrPatList1();
              }
              catch (Exception ex)
              {
                  MessageManager.showException(ex.getMessage(),ex);
                  logger.debug(ex.getMessage(),ex);
              }
          }
      });
      
      queryButton2.addActionListener(new ActionListener(){
          public void actionPerformed(ActionEvent e){
              try
              {
                  loadEmrPatList2();
              }
              catch (Exception ex)
              {
                  MessageManager.showException(ex.getMessage(),ex);
                  logger.debug(ex.getMessage(),ex);
              }
          }
      });
      
      queryButton3.addActionListener(new ActionListener(){
          public void actionPerformed(ActionEvent e){
              try
              {
                  loadEmrPatList3();
              }
              catch (Exception ex)
              {
                  MessageManager.showException(ex.getMessage(),ex);
                  logger.debug(ex.getMessage(),ex);
              }
          }
      });
                
        /**
         * ��OLOV
         * */
        btnQueryDept.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                	DeptLovDialog deptLovDialog = new DeptLovDialog(getInstance());
                }
                catch (Exception ex)
                {
                    MessageManager.showException(ex.getMessage(),ex);
                    logger.debug(ex.getMessage(),ex);
                }
            }
        });
        
        /**
         * �D�v��vLOV
         * */
        btnQueryVs.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                	VsLovDialog vsLovDialog = new VsLovDialog(getInstance());
                }
                catch (Exception ex)
                {
                    MessageManager.showException(ex.getMessage(),ex);
                    logger.debug(ex.getMessage(),ex);
                }
            }
        });
        
        /**
         * �@�z��LOV
         * */
        btnQueryStation.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                	StationLovDialog stationLovDialog = new StationLovDialog(getInstance());
                }
                catch (Exception ex)
                {
                    MessageManager.showException(ex.getMessage(),ex);
                    logger.debug(ex.getMessage(),ex);
                }
            }
        });
        
        //��OTextField
        deptCodeField.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    loadEmrPatList();
                }
            }
        });
        
        //�@�z��TextField
        branchCodeField.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    loadEmrPatList();
                }
            }
        });
        
        //��|��
        beHopRdo1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				beInHospitalFlag = true;
			}
        });
        //�w�X�|(�w�]�C��)
        beHopRdo2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				beInHospitalFlag = false;
			}
        });
        
        //----------�d�߭����\��s End----------
  }
  
    //update by leon 2012-06-08:�C�L�ɨϥ�THREAD����
    class PrintThread extends Thread
    {        
        String noteType;
        String branchCode;
        String documentId;
        String encounterNo;
        String parms;
        String copies;
        String chartNo;
        String groupCode;
        String strTitle;
        
        PrintThread(String noteType,String branchCode,String documentId,String encounterNo,String parms,String copies,String chartNo,String groupCode, String strTitle)
        {                        
            this.noteType = noteType;
            this.branchCode = branchCode;
            this.documentId = documentId;
            this.encounterNo = encounterNo;
            this.parms = parms;
            this.copies = copies;
            this.chartNo = chartNo;                
            this.groupCode = groupCode;
            this.strTitle = strTitle;
        }
                    
        public void run()
        {
            try
            {                       
                if (this.groupCode == null) {//����
                    NoteReport.print(noteType,branchCode,currentVO.getDocumentId(),GlobalParameters.getInstance().getEncounterNo(),parms,copies,GlobalParameters.getInstance().getChartNo());
                    logger.debug("�C�L����");
                } else {
                    RepBasStub stub = new RepBasStub();
                    stub.setEndpoint(GlobalParameters.getInstance().getReportServerURL());
                    ResultDTO dto = stub.printNoteReport(noteType,branchCode,currentVO.getDocumentId(),GlobalParameters.getInstance().getEncounterNo(),parms,copies,GlobalParameters.getInstance().getChartNo(),groupCode);
                    if(dto.getMessage().length() > 0)
                    {
                        JOptionPane.showMessageDialog(null,"�C�L�^�ǰT��:" + dto.getMessage());
                    }
                }
            }
            catch (Exception ex)
            {
                MessageManager.showException(ex.getMessage(),ex);
                logger.debug(ex.getMessage(),ex);
            } 
        }
    }
 
    /**
     * �]�w�X�|�K�n�w�]��(���J�|�K�n���`���e��J�X�|�K�n���`)
     * update by leon 2012-03-27:���g�X�K�ɹw�]�a�J�K���,�]�J�K��ƥi�༶�g�h��,�®ɬ��������a�i��,�s�קאּ�u�a�J�̫�@���J�K���
     */
    public void setDefaultDischgNoteData()
    {
        try
        {
            String sql = "";
            sql += " SELECT ";
            sql += "    * ";
            sql += " FROM ";
            sql += "    emr_pat_note ";
            sql += " WHERE encounter_no = '" + GlobalParameters.getInstance().getEncounterNo() + "' ";
            //sql += "    AND sheet_id = 'AdminNote' AND report_status_scid = '2' ";
            sql += "    AND sheet_id = 'AdminNote' ";
            sql += " ORDER BY ";
            sql += "    date_created DESC "; /**�ѷs���±Ƨ�*/
            
            //���o���           
            ArrayList list = EmrPatNoteDAO.getInstance().findAllBySql(sql);

            String chiefComplaint = "";
            String personalHistory = "";
            String physicalExam = "";
            String labFinding = "";
            String impression = "";
            String presentIllness = "";
            String pastHistory = "";
            String familyHistory = "";
            if (list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    //�����`
                    EmrPatNoteVO vo = (EmrPatNoteVO) list.get(i);
                    /*
                    chiefComplaint += "<br>" + handler.getChapterString("02.Chief Complaint", vo.getDocumentText());
                    presentIllness += "<br>" + handler.getChapterString("03.Present Illness", vo.getDocumentText());
                    personalHistory += "<br>" + handler.getChapterString("05.Personal History", vo.getDocumentText());
                    physicalExam += "<br>" + handler.getChapterString("08.Physical Exam", vo.getDocumentText());
                    labFinding += "<br>" + handler.getChapterString("09.Lab Finding", vo.getDocumentText());
                    impression += "<br>" + handler.getChapterString("10.Impression", vo.getDocumentText());
                    */
                    chiefComplaint += handler.getChapterString("02.Chief Complaint", vo.getDocumentText());
                    presentIllness += handler.getChapterString("03.Present Illness", vo.getDocumentText());
                    pastHistory += handler.getChapterString("04.Past History", vo.getDocumentText());
                    personalHistory += handler.getChapterString("05.Personal History", vo.getDocumentText());
                    familyHistory += handler.getChapterString("06.Family History", vo.getDocumentText());
                    physicalExam += handler.getChapterString("08.Physical Exam", vo.getDocumentText());
                    labFinding += handler.getChapterString("09.Lab Finding", vo.getDocumentText());
                    impression += handler.getChapterString("10.Impression", vo.getDocumentText());
                    break; /**�u�B�z�Ƨǫ᪺�Ĥ@��(�u��̷s�����)*/
                }
                //��J���
                HashMap dischgNoteDefaultValue = new HashMap();
                dischgNoteDefaultValue.put("01.�J�|�E�_", impression);
                //dischgNoteDefaultValue.put("02.�X�|�E�_", impression);
                dischgNoteDefaultValue.put("03.�D  �z", chiefComplaint);
                //dischgNoteDefaultValue.put("04.�f  �v", presentIllness + "\n" + pastHistory + "\n" + personalHistory + "\n" + familyHistory);
                String _04 = "<p><b>Present Illness:</b></p>" + presentIllness +
	                		 "<p><b>Past History:</b></p>" + pastHistory +
	                		 "<p><b>Personal History:</b></p>" + personalHistory +
	                		 "<p><b>Family History:</b></p>" + familyHistory;
                dischgNoteDefaultValue.put("04.�f  �v", _04);
                dischgNoteDefaultValue.put("05.���˵o�{", physicalExam);
                dischgNoteDefaultValue.put("09.�ˬd�O��", labFinding);
                //��s�e��
                setNoteTemplate(dischgNoteDefaultValue, false);
            }
        } catch (Exception ex) {
            MessageManager.showException(ex.getMessage(), ex);
            logger.debug(ex.getMessage(), ex);
        }
    }

    public void setDefaultData(EmrPatNoteVO newVO) {
        try {
            String sql = "select * from emr_sheet_definition where sheet_id = '" + filterString + "'";
            //���o���           
            ArrayList list = EmrSheetDefinitionDAO.getInstance().findAllBySql(sql);

            if (list.size() > 0) {
                EmrSheetDefinitionVO vo = (EmrSheetDefinitionVO) list.get(0);
                //�]�w���
                newVO.setSheetId(vo.getSheetId());
                newVO.setReportTypeScid(vo.getSheetType());
                newVO.setReportSubtypeScid(vo.getSheetSubType());
                newVO.setReportThirdTypeScid(vo.getSheetThirdType());
            }
        } catch (Exception ex) {
            MessageManager.showException(ex.getMessage(), ex);
            logger.debug(ex.getMessage(), ex);
        }
    }
    
    /**
     * �]�wEkit Document id
     * @param documentId
     */
    public void setEkitCoreDocumentId(String documentId) {
        noteEkitPanel.getEkitPanel().getEkitCore().documentId = documentId;
    }

    /**
     * �ˬd��ƬO�_���ק�
     * @see
     */
    public boolean checkData() {
    	boolean flag = false;
        try {
            if (currentVO != null) {
                //���VO��ƬO�_�ܧ�
                //Win7 1.5��Java�|��TimeZone���D
            	//System.out.println("@getRowStatus: " + currentVO.getRowStatus());
            	
            	TimeZone.setDefault(TimeZone.getTimeZone("Asia/Taipei"));
                binding.setVo(currentVO);
                binding.compareData();

                //System.out.println("@getRowStatus: " + currentVO.getRowStatus());
                
                if (currentVO != null && !currentVO.getRowStatus().equals("R")) {
                    //int op = JOptionPane.showConfirmDialog(null, "��Ƥw���ק�A�O�_���}�H", "Info", JOptionPane.OK_CANCEL_OPTION);
                	int op = JOptionPane.showOptionDialog(null, "��Ƥw���ק�A�O�_���}�H", "Info",
                			JOptionPane.OK_CANCEL_OPTION, -1, null, new String[]{"�O", "�_"}, "�O");
                    if (op == JOptionPane.YES_OPTION) {
                    	rollBack();
                    } else {
                    	flag = true;
                    }
                }
            }
        } catch (Exception ex) {
            MessageManager.showException(ex.getMessage(), ex);
            logger.debug(ex.getMessage(), ex);
        }
        return flag;
    }
    
    /**
     * ���VO���(EmrPatNoteVO)�O�_�ܧ�
     */
    public void checkData2()
    {
        try
        {
            if(currentVO != null)
            {
                //���VO��ƬO�_�ܧ�
                TimeZone.setDefault(TimeZone.getTimeZone("Asia/Taipei"));
                binding.setVo(currentVO);
                binding.compareData();
                if(currentVO != null && !currentVO.getRowStatus().equals("R") && !currentVO.getRowStatus().equals("C"))
                {
                    currentVO.setRowStatus("U");                    
                }
            }
        }
        catch(Exception ex)
        {
            MessageManager.showException(ex.getMessage(), ex);
            logger.debug(ex.getMessage(), ex);
        }
    }
        
    /**
     * �s�ɫe�]�֬O�_�i�H�s��
     * */
    public boolean limitsOfSave(boolean saveFlag)
    {
    	boolean rtnLimistFlag = true; //�w�]���i�s��    	
        
        //�����s���]��    	 
    	if(saveFlag)
    	{ 
    	    rtnLimistFlag = limitsOfSave_2();
    	}
    	//�Ȧs�ê��]��    	 
    	else
    	{    	    
    	    rtnLimistFlag = limitsOfSave_1();
    	}
    	
    	return rtnLimistFlag;
    }
    
    /**
     * �d�ߧY�ɪ��E�_��T
     * */
    public String getDataObjectString(String dataobjectCode)
    {
        String result = "";
    	    	    	
        try
	{
            String sql = "select * from Emr_Note_Dataobject where dataobject_Code = 'DischargeDiagnosis'";
            ArrayList rs = EmrNoteDataobjectDAO.getInstance().findAllBySql(sql);
			
            if(rs.size() > 0)
            {                            
                DocDataObjectStub stub = new DocDataObjectStub();
                stub.setEndpoint(GlobalParameters.getInstance().getDocServerURL());
		notews.ResultDTO dto = stub.singleParse(GlobalParameters.getInstance().getEncounterNo(), GlobalParameters.getInstance().getChartNo(), "DischargeDiagnosis", "");				
                                                               
		if(dto.getResult() != null && dto.getResult().length() > 0)
		{                                                                
                    //���o�^�Ǧr��óB�z
                    String returnStr = dto.getResult();
					
                    returnStr = StringUtil.fixSpace("&#160;",returnStr);
                    returnStr = StringUtil.fixSpace("&nbsp;",returnStr);
		    returnStr = returnStr.replace("<","&lt;");
		    returnStr = returnStr.replace(">","&gt;");
                    returnStr = returnStr.replaceAll("\n","<br>");		    
                    result = "<p>" + returnStr + "</p>";
		}
            }
        }
	catch(Exception ex)
	{
            MessageManager.showException(ex.getMessage(), ex);
            logger.debug(ex.getMessage(), ex);
        }    	
    	
    	return result;
    }
           
    /*
     * save to emr dischg interface table.==>INFINITT EMR SYSTEM
     * througn Jericho HTML Parser 
     */
    public void saveEmrInterfaceTable() throws Exception {
        //String docText = "";
        int chapter_index = 0;
        
        EmrTempDischarge2VO  emrTempDischarge2VO = new  EmrTempDischarge2VO();
        EmrIpdEncounterVO emrIpdEncounterVO = GlobalParameters.getInstance().getEmrIpdEncounterVO();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd[HHmmss]"); 
        
        //�X�|������ť�,�h���g�J�q�l�f���t��
        //�վ�Y���ťձa�{�b���
        //if(StringUtil.isBlank(emrIpdEncounterVO.getCloseDate())) return;
        
        hm.put(oldSeledtedItem, noteEkitPanel.getEkitPanel().getEkitCore().getDocumentBody());
        Iterator it = hm.keySet().iterator();
        while (it.hasNext()) {
            chapter_index ++;
            String key = it.next().toString();
            String value = hm.get(key).toString();
            //System.out.println("chapter=" + key);
            //System.out.println("chapter HTML=" + value);
            Source source=new Source(value);
            String renderedText=source.getRenderer().toString();            
            //System.out.println("chapter TEXT=" + renderedText);
            switch (chapter_index){
                case 1:  //"01.�J�|�E�_"
                    if(StringUtil.isBlank(renderedText)){
                        emrTempDischarge2VO.setErdc2Impression1("Nil");
                    }else{
                        String[] textArray = StringUtil.splitByLen(renderedText, 4000);
                        for (int i=0; i<textArray.length; i++) {
                            try {
                                Method method = emrTempDischarge2VO.getClass().getMethod("setErdc2Impression"+(i+1), new Class[]{String.class});
                                method.invoke(emrTempDischarge2VO, textArray[i]);
                            }catch(NoSuchMethodException e){
                                System.out.println("NoSuchMethodException:" + e.getMessage());
                            }
                        }
                        //emrTempDischarge2VO.setErdc2Impression1(renderedText);
                    }
                    break;
                case 2: //"02.�X�|�E�_"
                    if(StringUtil.isBlank(renderedText)){
                        emrTempDischarge2VO.setErdc2Diagnosis1("Nil");
                    }else{
                        //emrTempDischarge2VO.setErdc2Diagnosis1(renderedText);
                        String[] textArray = StringUtil.splitByLen(renderedText, 4000);
                        for (int i=0; i<textArray.length; i++) {
                            try {
                                Method method = emrTempDischarge2VO.getClass().getMethod("setErdc2Diagnosis"+(i+1), new Class[]{String.class});
                                method.invoke(emrTempDischarge2VO, textArray[i]);
                            }catch(NoSuchMethodException e){
                                System.out.println("NoSuchMethodException:" + e.getMessage());
                            }
                        }
                    }
                    break;
                case 3:  //"03.�D �z":{
                    if(StringUtil.isBlank(renderedText)){
                        emrTempDischarge2VO.setErdc2Memo1("Nil");
                    }else{
                        //emrTempDischarge2VO.setErdc2Memo1(renderedText);
                        String[] textArray = StringUtil.splitByLen(renderedText, 4000);
                        for (int i=0; i<textArray.length; i++) {
                            try {
                                Method method = emrTempDischarge2VO.getClass().getMethod("setErdc2Memo"+(i+1), new Class[]{String.class});
                                method.invoke(emrTempDischarge2VO, textArray[i]);
                            }catch(NoSuchMethodException e){
                                System.out.println("NoSuchMethodException:" + e.getMessage());
                            }
                        }
                    }
                    break;
                case 4:  //"04.�f �v":{
                    if(StringUtil.isBlank(renderedText)){
                        emrTempDischarge2VO.setErdc2Medicalhistory1("Nil");
                    }else{            
                        //emrTempDischarge2VO.setErdc2Medicalhistory1(renderedText);
                        String[] textArray = StringUtil.splitByLen(renderedText, 4000);
                        for (int i=0; i<textArray.length; i++) {
                            try {
                                Method method = emrTempDischarge2VO.getClass().getMethod("setErdc2Medicalhistory"+(i+1), new Class[]{String.class});
                                method.invoke(emrTempDischarge2VO, textArray[i]);
                            }catch(NoSuchMethodException e){
                                System.out.println("NoSuchMethodException:" + e.getMessage());
                            }
                        }
                    }
                    break;
                case 5:  //"05.���˵o�{":{
                    if(StringUtil.isBlank(renderedText)){
                        emrTempDischarge2VO.setErdc2Physicalexam1("Nil");
                    }else{               
                        //emrTempDischarge2VO.setErdc2Physicalexam1(renderedText);
                        String[] textArray = StringUtil.splitByLen(renderedText, 4000);
                        for (int i=0; i<textArray.length; i++) {
                            try {
                                Method method = emrTempDischarge2VO.getClass().getMethod("setErdc2Physicalexam"+(i+1), new Class[]{String.class});
                                method.invoke(emrTempDischarge2VO, textArray[i]);
                            }catch(NoSuchMethodException e){
                                System.out.println("NoSuchMethodException:" + e.getMessage());
                            }
                        }
                    }
                    break;
                case 6:  //"06.��N����Τ�k":{
                    if(StringUtil.isBlank(renderedText)){
                        emrTempDischarge2VO.setErdc2Surgical1("Nil");
                    }else{
                        //emrTempDischarge2VO.setErdc2Surgical1(renderedText);
                        String[] textArray = StringUtil.splitByLen(renderedText, 4000);
                        for (int i=0; i<textArray.length; i++) {
                            try {
                                Method method = emrTempDischarge2VO.getClass().getMethod("setErdc2Surgical"+(i+1), new Class[]{String.class});
                                method.invoke(emrTempDischarge2VO, textArray[i]);
                            }catch(NoSuchMethodException e){
                                System.out.println("NoSuchMethodException:" + e.getMessage());
                            }
                        }
                    }
                    break;
                case 7:  //"07.��|�v���g�L":{
                    if(StringUtil.isBlank(renderedText)){
                        emrTempDischarge2VO.setErdc2Course1("Nil");
                    }else{
                        //emrTempDischarge2VO.setErdc2Course1(renderedText);
                        String[] textArray = StringUtil.splitByLen(renderedText, 4000);
                        for (int i=0; i<textArray.length; i++) {
                            try {
                                Method method = emrTempDischarge2VO.getClass().getMethod("setErdc2Course"+(i+1), new Class[]{String.class});
                                method.invoke(emrTempDischarge2VO, textArray[i]);
                            }catch(NoSuchMethodException e){
                                System.out.println("NoSuchMethodException:" + e.getMessage());
                            }
                        }
                    }
                    break;
                case 8:  //"08.�X�֯g":{
                    if(StringUtil.isBlank(renderedText)){
                        emrTempDischarge2VO.setErdc2Comorbidites1("Nil");
                    }else{
                        //emrTempDischarge2VO.setErdc2Comorbidites1(renderedText);
                        String[] textArray = StringUtil.splitByLen(renderedText, 4000);
                        for (int i=0; i<textArray.length; i++) {
                            try {
                                Method method = emrTempDischarge2VO.getClass().getMethod("setErdc2Comorbidites"+(i+1), new Class[]{String.class});
                                method.invoke(emrTempDischarge2VO, textArray[i]);
                            }catch(NoSuchMethodException e){
                                System.out.println("NoSuchMethodException:" + e.getMessage());
                            }
                        }
                    }
                    break;
                case 9:  //"09.�ˬd�O��":{
                    if(StringUtil.isBlank(renderedText)){
                        emrTempDischarge2VO.setErdc2LaboratoryClob1("Nil");
                    }else{
                        //emrTempDischarge2VO.setErdc2LaboratoryClob1(renderedText);
                        String[] textArray = StringUtil.splitByLen(renderedText, 4000);
                        for (int i=0; i<textArray.length; i++) {
                            try {
                                Method method = emrTempDischarge2VO.getClass().getMethod("setErdc2LaboratoryClob"+(i+1), new Class[]{String.class});
                                method.invoke(emrTempDischarge2VO, textArray[i]);
                            }catch(NoSuchMethodException e){
                                System.out.println("NoSuchMethodException:" + e.getMessage());
                            }
                        }
                    }
                    break;
                case 10:  //"10.��g�u���i":{
                    if(StringUtil.isBlank(renderedText)){
                        emrTempDischarge2VO.setErdc2Spcexam1("Nil");
                    }else{
                        //emrTempDischarge2VO.setErdc2Spcexam1(renderedText);
                        String[] textArray = StringUtil.splitByLen(renderedText, 4000);
                        for (int i=0; i<textArray.length; i++) {
                            try {
                                Method method = emrTempDischarge2VO.getClass().getMethod("setErdc2Spcexam"+(i+1), new Class[]{String.class});
                                method.invoke(emrTempDischarge2VO, textArray[i]);
                            }catch(NoSuchMethodException e){
                                System.out.println("NoSuchMethodException:" + e.getMessage());
                            }
                        }
                    }
                    break;
                case 11:  //"11.�f�z���i":{
                    if(StringUtil.isBlank(renderedText)){
                        emrTempDischarge2VO.setErdc2Pathologyexam1("Nil");
                    }else{
                        //emrTempDischarge2VO.setErdc2Pathologyexam1(renderedText);
                        String[] textArray = StringUtil.splitByLen(renderedText, 4000);
                        for (int i=0; i<textArray.length; i++) {
                            try {
                                Method method = emrTempDischarge2VO.getClass().getMethod("setErdc2Pathologyexam"+(i+1), new Class[]{String.class});
                                method.invoke(emrTempDischarge2VO, textArray[i]);
                            }catch(NoSuchMethodException e){
                                System.out.println("NoSuchMethodException:" + e.getMessage());
                            }
                        }
                    }
                    break;
                case 12:  //"12.��L":{
                    
                    break;
                case 13:  //"13.�X�|�ɱ���":{
                    if(StringUtil.isBlank(renderedText)){
                        emrTempDischarge2VO.setErdc2Outstatus1("Nil");
                    }else{    
                        //emrTempDischarge2VO.setErdc2Outstatus1(renderedText);
                        String[] textArray = StringUtil.splitByLen(renderedText, 4000);
                        for (int i=0; i<textArray.length; i++) {
                            try {
                                Method method = emrTempDischarge2VO.getClass().getMethod("setErdc2Outstatus"+(i+1), new Class[]{String.class});
                                method.invoke(emrTempDischarge2VO, textArray[i]);
                            }catch(NoSuchMethodException e){
                                System.out.println("NoSuchMethodException:" + e.getMessage());
                            }
                        }
                    }
                    break;
                case 14:  //"14.�X�|����":{
                    if(StringUtil.isBlank(renderedText)){
                        emrTempDischarge2VO.setErdc2Outplan1("Nil");
                    }else{
                        //emrTempDischarge2VO.setErdc2Outplan1(renderedText);
                        String[] textArray = StringUtil.splitByLen(renderedText, 4000);
                        for (int i=0; i<textArray.length; i++) {
                            try {
                                Method method = emrTempDischarge2VO.getClass().getMethod("setErdc2Outplan"+(i+1), new Class[]{String.class});
                                method.invoke(emrTempDischarge2VO, textArray[i]);
                            }catch(NoSuchMethodException e){
                                System.out.println("NoSuchMethodException:" + e.getMessage());
                            }
                        }
                    }
                    break;
            }
        }
         if (emrTempDischarge2VO != null ) {
             try {
                 //�B�z��semrTempDischarge2VO others�򥻸��
                 //currentVO.setReportStatusScid(saveStatus);
                 //currentVO.setReportDate(new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(eventTimeTextField.getText()).getTime()));

                 //INSERT�ʧ@
                 //loadEmrIpdEncounterData(); //load EmrIpdEncounterData to global variable
                 emrTempDischarge2VO.setErdc2Referno(currentVO.encounterNo + currentVO.documentId);//��|�Ǹ�+�f�K�y����
                 
                 //update by leon 2016-02-23(�G):FOR����
                 String hospitalCode = StringUtil.getHospitalCode();
                 emrTempDischarge2VO.setErdc2Location(hospitalCode);
                 
                 emrTempDischarge2VO.setErdc2Patidtype("I");  //�����Ҹ�����                                
                 emrTempDischarge2VO.setErdc2Patidnumber(emrIpdEncounterVO.getIdNo()); //�f�w�����Ҹ�                 
                 emrTempDischarge2VO.setErdc2Patno(currentVO.getChartNo()); //�f���� 
                 emrTempDischarge2VO.setErdc2Patname(emrIpdEncounterVO.getPatName());//�f�w�m�W
                 emrTempDischarge2VO.setErdc2Sex(emrIpdEncounterVO.getSexType()); //�ʧO
                 
                 emrTempDischarge2VO.setErdc2Patbirth(sdf.format(emrIpdEncounterVO.getBirthDate())); //�X�ͤ��
                 emrTempDischarge2VO.setErdc2Patage(new BigDecimal(emrIpdEncounterVO.getAge()));  //�f�w�~��
                 emrTempDischarge2VO.setErdc2Xxdep(emrIpdEncounterVO.getDeptCode());//�X�|��O 
                 emrTempDischarge2VO.setErdc2Room(emrIpdEncounterVO.getBedNo());//�f�ɧɸ�
                 emrTempDischarge2VO.setErdc2Indate(sdf.format(emrIpdEncounterVO.getAdmitDate())); //��|���
                 if(StringUtil.isBlank(emrIpdEncounterVO.getCloseDate())){
                    emrTempDischarge2VO.setErdc2Outdate(sdf.format(Calendar.getInstance().getTime()));
                 }else{
                    emrTempDischarge2VO.setErdc2Outdate(sdf.format(emrIpdEncounterVO.getCloseDate())); //�X�|���
                 }
                    
                 /**
                  * update by leon 2015-12-29
                  * */
                 String strCloseDate = DateUtil.getCloseDate(emrIpdEncounterVO.getEncounterNo());
                 String strIerIcdTenDate = DateUtil.getIerIcdTenDate();
                 
                 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");                
                 Date closeDate = df.parse(strCloseDate); 
                 Date ierIcdTenDate = df.parse(strIerIcdTenDate);
                 
                 long dayDif = (closeDate.getTime() - ierIcdTenDate.getTime());
                 if(dayDif < 0)
                 {
                     //�E�_ICD9�G'@' �Ϥ��h��
                     String sql = "";
                     sql = sql + " SELECT ";
                     sql = sql + "     * ";
                     sql = sql + " FROM ";
                     sql = sql + "     ier_pat_diag ";
                     sql = sql + " WHERE ";
                     sql = sql + "     encounter_no = '" + emrIpdEncounterVO.getEncounterNo() + "' ";
                     sql = sql + "     AND diag_type = 'O' ";
                     sql = sql + "     AND status = 'A' ";
                     sql = sql + " ORDER BY ";
                     sql = sql + "     diag_seq ";
                     
                     ArrayList list = IerPatDiagDAO.getInstance().findAllBySql(sql);
                     String diaicd9 = "";
                     for (int i=0;i < list.size(); i++)
                     {
                        IerPatDiagVO ierPatDiagVO = (IerPatDiagVO)list.get(i);
                        if(i==0)
                        {
                            diaicd9 = ierPatDiagVO.getIcdcode();
                        }
                        else
                        {
                            diaicd9 = diaicd9 + "@" + ierPatDiagVO.getIcdcode();
                        }                        
                     }
                     emrTempDischarge2VO.setErdc2Diaicd9(diaicd9);
                 }
                 else
                 {
                     //�E�_ICD10�G'@' �Ϥ��h��                                           
                     String strIcd10 = StringUtil.getIcd10StrEec(emrIpdEncounterVO.getEncounterNo(),emrIpdEncounterVO.getPatState());
                     emrTempDischarge2VO.setErdc2Diaicd9(strIcd10);
                 }                                                 

                 emrTempDischarge2VO.setErdc2Doctoruserid(emrIpdEncounterVO.getVsCode()); //ñ����v   
                 emrTempDischarge2VO.setErdc2Doctoruserdep(emrIpdEncounterVO.getDeptCode()); // ñ����v��O
                 emrTempDischarge2VO.setErdc2Doctoruserid(emrIpdEncounterVO.getVsCode()); // �D�v��v
                 emrTempDischarge2VO.setErdc2Secretcode("1"); //���K�X
                 //emrTempDischarge2VO.setErdc2Secretlv(0); ���K���� 
                 emrTempDischarge2VO.setErdc2Infsource("HIS"); //��ƨӷ� 
                 emrTempDischarge2VO.setPostFlag("N");
                                  
                 EmrTempDischarge2DAO.getInstance().insertData(emrTempDischarge2VO);
                 //update by leon 2015-03-06: FOR���O�p�X�K���ɭp�e
                 EmrTempDischarge2DAO.getInstance().insertDischgEecData(emrTempDischarge2VO);
             } catch (Exception ex) {
                 MessageManager.showException(ex.getMessage(), ex);
                 logger.debug(ex.getMessage(), ex);
             }
         }  
        
    }
        
    /**
     * �s��
     * @param saveStatus 1:�Ȧs /2:����
     */
    public void saveData(String saveStatus)
    {
        //�s�ɫ�����^"�f�����"����
        jideTabbedPane.setSelectedIndex(1); //Trigger �����ʧ@
    	
        if(currentVO != null && !currentVO.getRowStatus().equals("R"))
        {
            try
            {
                //�B�z����(�Ϥ�)
            	long ckTD01 = System.currentTimeMillis();            	                
                ArrayList al = imageUtil.replaceTemplateImage(htmlViewer.getEkitPanel().getEkitCore().getImageNameList(), currentVO);
                imageUtil.saveImage2DB(al, currentVO);                
                long ckTD02 = System.currentTimeMillis();
                //System.out.println("step saveImage2DB Time:" + (ckTD02 - ckTD01));
                                
                currentVO.setReportStatusScid(saveStatus); //�s�ɪ��A(1:�Ȧs�B2:����)
                currentVO.setReportDate(new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(eventTimeTextField.getText()).getTime())); //���i���
                currentVO.setLastUpdated(new java.util.Date()); //���ʤ��
                currentVO.setLastUpdatedBy(GlobalParameters.getInstance().getLoginUserName()); //���ʪ�
                currentVO.setPersonInCharge(this.txtPersonInChargeCode.getText()); //��ñ���D�v��v
                
                //update by leon 2012-08-13:�v���B�m�l���O�y�z
                if("TreNote".equals(filterString))
                {
                    currentVO.setExamOrderDesc(treNoteComboBox.getSelectedItem().toString());                    
                }
                
                //����
                if (currentVO.getDocumentVersion() != null)
                {
                    Integer version = new Integer(currentVO.getDocumentVersion().intValue() + 1);   //�P�ɶ��G�H�H�W�s��|��Bug
                    currentVO.setDocumentVersion(version);
                }
                else
                {
                    currentVO.setDocumentVersion(new Integer(1));
                }                                                                                         
                
                //update by leon 2012-04-30:�q�l�f���A�ˮ�EMR �S��r�����D
                currentVO.documentText = currentVO.documentText.replaceAll("[\\x01-\\x08\\x0B-\\x0C\\x0E-\\x1F\\x7F-\\x84\\x86-\\x9F]", "");
                
                //replace print error
                currentVO.documentText = currentVO.documentText.replaceAll("bgcolor=\"none\"", "");
                
                //update by leon 2014-02-24: ñ���W�h
                //����(�u���D�v��v�i�H����)(�ۦ�ñ��)
                if("2".equals(saveStatus))
                {                	                                        
                    currentVO.setAdviserByScid(GlobalParameters.getInstance().getSecUserVO().getUsername());
                    currentVO.setAdviserByName(GlobalParameters.getInstance().getSecUserVO().getUserRealName());                                       
                }
                //�Ȧs
                else
                {
                    //��|��v(�ۦ�ñ��)
                    if("1".equals(saveStatus) && "D2".equals(GlobalParameters.getInstance().getSecUserVO().getUserGroup()))
                    {
                        //update by leon 2014-06-19:setEnteredByScid��ñ�����x�����g��
                	currentVO.setEnteredByScid(GlobalParameters.getInstance().getSecUserVO().getUsername());
                	currentVO.setEnteredByName(GlobalParameters.getInstance().getSecUserVO().getUserRealName());
                        currentVO.setAdviserByScid(GlobalParameters.getInstance().getSecUserVO().getUsername());
                        currentVO.setAdviserByName(GlobalParameters.getInstance().getSecUserVO().getUserRealName());
                    }
                    //�M�v(�ۦ�ñ��)                	
                    else if("1".equals(saveStatus) && "A0".equals(GlobalParameters.getInstance().getSecUserVO().getUserGroup()))
                    {
                        //update by leon 2014-06-19:setEnteredByScid��ñ�����x�����g��
                	currentVO.setEnteredByScid(GlobalParameters.getInstance().getSecUserVO().getUsername());
                	currentVO.setEnteredByName(GlobalParameters.getInstance().getSecUserVO().getUserRealName());                		
                	currentVO.setAdviserByScid(GlobalParameters.getInstance().getSecUserVO().getUsername());
                        currentVO.setAdviserByName(GlobalParameters.getInstance().getSecUserVO().getUserRealName());
                    }                	
                    //�����v(��ñ���D�v��v�I��)
                    else
                    {
                        //update by leon 2014-06-19:setEnteredByScid��ñ�����x�����g��
                	currentVO.setEnteredByScid(GlobalParameters.getInstance().getSecUserVO().getUsername());
                	currentVO.setEnteredByName(GlobalParameters.getInstance().getSecUserVO().getUserRealName());                		                		
                	currentVO.setAdviserByScid(currentVO.getPersonInCharge());
                	currentVO.setAdviserByName(currentVO.getPersonInChargeName());
                    }
                }                               
                                                                                              
                //�X�K�s�ɦ۰ʧ�s�Y�ɪ��X�|�E�_
                //�X�|�� 104-08-10�H��Ұ�                
                if("DischgNote".equals(currentVO.getSheetId()) && "D".equals(currentVO.getReportThirdTypeScid()))
                {                    
                    Date closeDate = GlobalParameters.getInstance().getEmrIpdEncounterVO().getCloseDate();                
                    String strCloseDate = DateUtil.getDateString(closeDate,"yyyy-MM-dd");                
                    String strCompareDate = "2015-08-10";
                    
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");                
                    closeDate = df.parse(strCloseDate); 
                    Date compareDate = df.parse(strCompareDate);                                                         
                    long dayDif = (closeDate.getTime() - compareDate.getTime());                                            
                    if(dayDif >= 0)
                    {
                        //�d�ߧY�ɪ��E�_��T
                        String dataObjectStr = getDataObjectString("DischargeDiagnosis");
                            
                        //���N�즳���E�_��T
                        String tmpDocumentText = currentVO.getDocumentText();
                        int token = tmpDocumentText.indexOf("<02.�X�|�E�_>") + 9;
                        String tmpDocumentText_1 = tmpDocumentText.substring(0, token);
                                                    
                        token = tmpDocumentText.indexOf("</02.�X�|�E�_>");
                        String tmpDocumentText_2 = tmpDocumentText.substring(token);
                            
                        tmpDocumentText = tmpDocumentText_1 + dataObjectStr + tmpDocumentText_2;
                        
                        currentVO.setDocumentText(tmpDocumentText);
                    }
                 }                 
                
                //save1:emr_pat_note
                ArrayList saveList = new ArrayList();                                                                           
                saveList.add(currentVO);                                                                                                   
                EmrPatNoteDAO.getInstance().updateData(saveList);                
                
                //save2:emr_pate_note_log
                if("OpNote".equals(filterString))
                {
                    OrReport orReport = new OrReport(currentVO.getSheetXmlMeta());
                    if("Y".equals(orReport.getOrVo().feeOpFlag))
                    {
                        EmrPatNoteLogDAO.getInstance().insertData(currentVO);
                    }
                }
                else
                {
                    EmrPatNoteLogDAO.getInstance().insertData(currentVO);
                }
                                            
                //save3:�X�|�f�K�eINFINITT�q�l�f�����x(EEC) AND ���O�p   
                if("DischgNote".equals(currentVO.getSheetId()) && "D".equals(currentVO.getReportThirdTypeScid()))
                {
                    saveEmrInterfaceTable();
                }
                
                //save4: �e�����q�lñ�����xPOHAI
                if(getRptdbFlag())
                {                	
                    doEmrSheet("C");
                }
                
                /**
                 * 0.����
                 * 1.��f���q�f���L
                 * 2.�s�ɫ�f���q�f�q�L
                 * 3.�f�w�w�X�|
                 * 4.�̫᧹���� != �̫�D�v
                 * 5.�q���̫�D�v�i�i��ñ���F
                 * */
                
                //����
                if("2".equals(saveStatus))
                {
                    //�q�f������
                    if(getEmrPatNoteErrlogCount() > 0)
                    {
                        //�q�f                         
                        Connection conn;
                        CallableStatement stproc_stmt;
                        try
                        {            
                            conn = DBConnectionHelper.getInstance().getMySqlConnection();
                            stproc_stmt = conn.prepareCall("{call P_CHECK_MEDICAL_CHART(?,?)}");                         
                            stproc_stmt.registerOutParameter(2, OracleTypes.VARCHAR);
                            stproc_stmt.setString(1, GlobalParameters.getInstance().getEncounterNo());
                            stproc_stmt.execute();                                                     
                            stproc_stmt.close();
                        }
                        catch(Exception e)
                        {
                            showMessage(e.toString());
                        }                        
                                             
                        //�q�f����
                        if(getEmrPatNoteErrlogCount() == 0)
                        {
                            //�w�X�|
                            if("D".equals(getPatState()))
                            {
                                //�̫�@���f�����v�d��v
                                System.out.println(currentVO.getPersonInCharge());
                                System.out.println(GlobalParameters.getInstance().getEmrIpdEncounterVO().vsCode);
                                System.out.println(currentVO.getPersonInChargeName());
                                if(!currentVO.getPersonInCharge().equals(GlobalParameters.getInstance().getEmrIpdEncounterVO().vsCode))
                                {
                                    System.out.println(currentVO.getPersonInCharge());
                                    System.out.println(GlobalParameters.getInstance().getEmrIpdEncounterVO().vsCode);
                                    System.out.println(currentVO.getPersonInChargeName());
                                    //�q���̫�D�v�i�i��ñ���F
                                    DocDataObjectStub stub = new DocDataObjectStub();
                                    stub.setEndpoint(GlobalParameters.getInstance().getDocServerURL());
                                    notews.ResultDTO dto = stub.singleParse(GlobalParameters.getInstance().getEncounterNo(), currentVO.getPersonInChargeName(), "MsgToFinalVs", "");                                    
                                }
                            }                         
                        }
                    }
                }
                                
                //refresh display name
                currentVO.setDisplayName(currentVO.getDisplayDocName());
                                
                //��s�e��
                loadPatNoteData();
                initNoteTextData();
                setEventTimeTextValue();
                setExamOrderDescValue();
                noteEkitPanel.setDataHasChangedStatus(false);
            }
            catch (Exception ex)
            {
                MessageManager.showException(ex.getMessage(), ex);
                logger.debug(ex.getMessage(), ex);
            }
        }
        
        
    }
        
    /**update by leon 2014-08-25: �X�K���X�|�E�_��Ū*/
    public void setDischgDiagReadOnly()
    {    	
    	String _selectedValue = (String)jList1.getSelectedValue();        
        
    	if("02.�X�|�E�_".equals(_selectedValue))
        {
                String _meanData = (String)dischgNoteComboBox.getSelectedItem();                
                _meanData = _meanData.split(":")[0];//DTC                
                
                if("T".equals(_meanData))
                {
                    noteEkitPanel.getEkitPanel().getEkitCore().getTextPane().setEditable(true); //�i�s��
                    noteEkitPanel.getEkitPanel().getEkitCore().getTextPane().setBackground(Color.WHITE); //�թ�
                     noteEkitPanel.getEkitPanel().getEkitCore().jbtnPaste.setEnabled(true);
                }
                else
                {
                    noteEkitPanel.getEkitPanel().getEkitCore().getTextPane().setEditable(false); //���i�s��
                    noteEkitPanel.getEkitPanel().getEkitCore().getTextPane().setBackground(Color.lightGray); //�ǩ�
                    noteEkitPanel.getEkitPanel().getEkitCore().jbtnPaste.setEnabled(false);
                }                
        }
        else
        {        	
        	noteEkitPanel.getEkitPanel().getEkitCore().getTextPane().setEditable(true); //�i�s��
        	noteEkitPanel.getEkitPanel().getEkitCore().getTextPane().setBackground(Color.WHITE); //�թ�
        	noteEkitPanel.getEkitPanel().getEkitCore().jbtnPaste.setEnabled(true);
        }        
    }
        
    private void executeDischgNoteRule(EmrPatNoteVO _currentVO)
    {
    	try
    	{
    		//update by leon 2014-02-19:�i�J�X�K�s��e���ɡA��ܥثe���l���O
            if("DischgNote".equals(filterString))
            {
        		if(currentVO != null)
        		{            			            		
        			String _selectedItemA = currentVO.getReportThirdTypeScid(); //�l���O�N�X
        			String _selectedItem = ""; //�l���O����W��
        			
        			if(StringUtil.isBlank(_selectedItemA))
        			{
        				_selectedItem = dischgNoteComboBoxShowData[0]; //�Y�l���O�N�X���Źw�]��D:�X�|�K�n
        			}
        			else
        			{
        				//�l���O�N�X�����ťH�j���粒��W��
        				for(int i = 0;i<dischgNoteComboBoxShowData.length;i++)
            			{
            				if(dischgNoteComboBoxShowData[i].split(":")[0].equals(_selectedItemA))
            				{
            					_selectedItem = dischgNoteComboBoxShowData[i];
            				}
            			}
        				
        				//�Y�N�X���s�b�A�w�]��D:�X�|�K�n
        				if(StringUtil.isBlank(_selectedItem))
            			{
            				_selectedItem = dischgNoteComboBoxShowData[0];
            			}
        			}            			        			            			            			            		        			        		
        			dischgNoteComboBox.setSelectedItem(_selectedItem);
        		}
        	}
    	}
    	catch(Exception ex)
    	{
    		MessageManager.showException(ex.getMessage(), ex);
    	}
    }
    
    private void executeProgressNoteRule(EmrPatNoteVO _currentVO)
    {
    	try
    	{
	    	if("ProgressNote".equals(filterString)) {
	    		if(_currentVO != null) {
	    			String _selectedItem = _currentVO.getReportThirdTypeScid();
	    			if(StringUtil.isBlank(_selectedItem)) {
	    				_selectedItem = wkSummaryComboBoxShowData[0]; //default:Progress Note
	    			}
	    			wkSummaryComboBox.setSelectedItem(_selectedItem);	    				    		
	    		}
	    		Date _admitDate = GlobalParameters.getInstance().getEmrIpdEncounterVO().getAdmitDate();
	    		
	    		//���o���W��
	    		
	    		Calendar calendar_admit = Calendar.getInstance();
	    		calendar_admit.setTime(_admitDate);	    		
	    		
	    		Calendar calendar_now = Calendar.getInstance();
	    		calendar_now.setTime(new Date());	    		
	    			    		
	    		//�ݭק�J�|��� 7 + 1�Ѵ���
	    		long timeNow = (calendar_now.getTimeInMillis() - calendar_admit.getTimeInMillis());
	    		long mins = timeNow/1000/60;
                        long hr = mins/60;
                        long day = hr/24;
	            
//	    		if(day % 7 == 0)
//	    		{
//	    			noteFormTitleHM.put("t5", "     ����:���������gWeekly summary");
//	    		}
//	    		else
//	    		{
//	    			if(noteFormTitleHM.containsKey("t5"))
//	    			{
//	    				noteFormTitleHM.remove("t5");
//	    			}
//	    		}	    		    		
		    	
		        this.setFormTitleHM(noteFormTitleHM);
	    	}
    	}
    	catch(Exception ex)
    	{
    		MessageManager.showException(ex.getMessage(), ex);
    	}
    }
    
    
    
    /**
     * �P�BTextPane�PSourcePane�����
     */
    public void syncTextPaneWithSourcePane() {
        if (noteEkitPanel.getEkitPanel().getEkitCore().isSourceWindowActive()) {
            noteEkitPanel.getEkitPanel().getEkitCore().getTextPane().setText(noteEkitPanel.getEkitPanel().getEkitCore().getSourcePane().getText());
        }
    }

    /**
     * �P�BHTML Viewer�PTextPane�����
     */
    public void syncViewerWithTextPane() {
        if (jList1.getSelectedValue() == null) {
            return;
        }
        hm.put(jList1.getSelectedValue().toString(), noteEkitPanel.getEkitPanel().getEkitCore().getDocumentBody());

        String viewText = "";
        Iterator it = emrNoteChapterData.iterator();
        while (it.hasNext()) {
            EmrNoteChapterVO chaptervo = (EmrNoteChapterVO) it.next();
            //���o���`���e
            viewText += "<b>" + chaptervo.getNoteChapter() + "</b><BR>" + hm.get(chaptervo.getNoteChapter()).toString() + "<BR>";
        }
        
        refreshViwer(viewText);
    }

    /**
     * �Neditor����ƭ��ըæ^�s��VO
     * @see
     */
    public void saveEditorText2Row()
    {
        String docText = getNewNoteString();
        if(currentVO != null)
        {
            if(currentVO.getDocumentText() != null)
            {
            	//System.out.println("-----------------------------------------");
            	//System.out.println("EkitCore is Changed::" + noteEkitPanel.getDataHasChangedStatus());
            	//JOptionPane.showMessageDialog(null, "EkitCore is Changed::" + noteEkitPanel.getDataHasChangedStatus());
                //System.out.println("-----------------------------------------");
                //System.out.println("currentVO:" + currentVO.getDocumentText());
                //System.out.println("docText:" + docText);
                //update by leon 2012-05-28:�קאּ�ϥ�MD5�����覡
                if(noteEkitPanel.getDataHasChangedStatus() && !StringUtil.encrypt(currentVO.getDocumentText()).equals(StringUtil.encrypt(docText)) && !currentVO.getRowStatus().equals("C"))
                {
                    currentVO.setRowStatus("U");
                    System.out.println("1");
                }
                //System.out.println("saveEditorText2Row RowStatus:" + currentVO.getRowStatus());
            } else if (!currentVO.getRowStatus().equals("C")) {
                currentVO.setRowStatus("U");
                System.out.println("2");
            }
            
            currentVO.setDocumentText(docText);
        }
    }    

    /**
     * ��l�f�����
     * @see
     */
    private void initNoteTextData() {
        hm = new TreeMap();
        
        String viewNoteText = this.getOriginalNoteString();
        //��s���`�M��  
        this.refreshChapterList();
        //��seditor
        this.refreshEditor();
        //��sviewer
        this.refreshViwer(viewNoteText);
    }

    /**
     * �N�ثe�beditor�s�誺��ƭ��ըæ^��
     * @see
     * @return
     */
    private String getNewNoteString() {
        //noteEkitPanel.getEkitPanel().getEkitCore().refreshOnUpdate();
        hm.put(oldSeledtedItem, noteEkitPanel.getEkitPanel().getEkitCore().getDocumentBody());
        String docText = "";
        Iterator it = hm.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next().toString();
            String value = hm.get(key).toString();
            
            //String phaseStr = "<" + key + ">\n" + value + "\n" + "</" + key + ">\n";
            String phaseStr = "<" + key + ">" + value + "</" + key + ">";

            docText = docText + phaseStr;
        }
        return docText;
    }

    /**
     * �NDB���f����Ʈھڳ��`parse��hashMap
     * @see
     * @return viewNoteText
     */
    private String getOriginalNoteString() {
        String viewNoteText = "";
        String noteText = "";
        chapterOrderArray = new ArrayList();
        if (currentVO != null) {
            String oldStr = currentVO.getDocumentSystemScid();
            noteText = currentVO.getDocumentText() != null ? currentVO.getDocumentText() : "";
            //System.out.println("noteText=" + noteText);
            Iterator it = emrNoteChapterData.iterator();
            while (it.hasNext()) {
                EmrNoteChapterVO chaptervo = (EmrNoteChapterVO) it.next();
                //���o���`���e
                String chapterStr = handler.getChapterString(chaptervo.getNoteChapter(), noteText);
               // System.out.println("chapter:"+chaptervo.getNoteChapter());
                if ("OLDHIS".equals(oldStr)) {
                    //chapterStr = chapterStr.replace("<","&lt;");
                    //chapterStr = chapterStr.replace(">","&gt;");
                    //chapterStr = chapterStr.replace("\n","<br>");
                }
                
                 //update by leon 2012-05-31:���{�����s�W�ɦbPLAN�϶��۰ʱa�J�f�w���D
//                 if("C".equals(currentVO.getRowStatus()) && "Plan".equals(chaptervo.getNoteChapter()) && "ProgressNote".equals(filterString))
//                 {
//                     try
//                     {
//                        String sql = "select * from Emr_Note_Dataobject where dataobject_Code = 'PatProblem'";
//                        ArrayList rs = EmrNoteDataobjectDAO.getInstance().findAllBySql(sql);
//                        if(rs.size() > 0)
//                        {                            
//                            DocDataObjectStub stub = new DocDataObjectStub();
//                            stub.setEndpoint(GlobalParameters.getInstance().getDocServerURL());
//                            notews.ResultDTO dto = stub.singleParse(GlobalParameters.getInstance().getEncounterNo(), GlobalParameters.getInstance().getChartNo(), "PatProblem", "");
//                                                                              
//                            if(dto.getResult() != null && dto.getResult().length() > 0)
//                            {                                                                
//                                //���o�^�Ǧr��óB�z
//                                String returnStr = dto.getResult();
//                                returnStr = returnStr.replaceAll("\n","<br>");
//                                returnStr = returnStr.replaceAll(" ","&nbsp;");
//                                returnStr = returnStr.replaceAll("<p>","<br>");
//                                returnStr = returnStr.replaceAll("</p>","");
//                                returnStr = returnStr.replaceAll("<pre>","<br>");
//                                returnStr = returnStr.replaceAll("</pre>","");
//                                
//                                chapterStr = "<p>" + returnStr + "</p>";
//                            }
//                        }
//                     }
//                     catch(Exception ex)
//                     {
//                        MessageManager.showException(ex.getMessage(), ex);
//                        logger.debug(ex.getMessage(), ex);
//                     }
//                }
//                
                viewNoteText += "<b>" + chaptervo.getNoteChapter() + "</b><BR>" + chapterStr + "<BR>";
                
                //�s�J�s�񪫥�  
                
                //System.out.println("key=" + chaptervo.getNoteChapter());
                hm.put(chaptervo.getNoteChapter(), chapterStr);
                //���`���List
                chapterOrderArray.add(chaptervo.getNoteChapter());
                chapterHm.put(chaptervo.getNoteChapter(), chaptervo.getPhraseType());
            }
        }

        //    System.out.println(origNoteText);
        return viewNoteText;
    }

    /**
     * ��s���`�M��
     * @see
     */
    private void refreshChapterList() {
        //�ܧ�e��
        jList1.setModel(getListModel());

        if (jList1.getModel().getSize() > 0) {
            jList1.setSelectedIndex(0);
            oldSeledtedItem = jList1.getSelectedValue().toString();
        } else {
            oldSeledtedItem = "";
        }
    }

    /**
     * ��seditor
     * @see
     */
    private void refreshEditor()
    {
        //���`�M�椣���ŭ�
        if(!StringUtil.isBlank(jList1.getSelectedValue()) && !StringUtil.isBlank(hm.get(jList1.getSelectedValue())))
        {
            noteEkitPanel.getEkitPanel().getEkitCore().getTextPane().setText(hm.get(jList1.getSelectedValue()).toString());
            noteEkitPanel.getEkitPanel().getEkitCore().getSourcePane().setText(hm.get(jList1.getSelectedValue()).toString());
        }
        else
        {
            //update by leon 2012-05-09:�ץ��Y�|����J���󤺮e�Y�ϥίf�w��ƥ\��δ��J�Ϥ��L�k���`��ܤ��e
            noteEkitPanel.getEkitPanel().getEkitCore().getTextPane().setText("<p></p>");
            noteEkitPanel.getEkitPanel().getEkitCore().getSourcePane().setText("<p></p>");
        }
    }

    /**
     * ��sviewer
     * @see
     * @param viewNoteText
     */
    private void refreshViwer(String viewNoteText) {
        htmlViewer.getEkitPanel().getEkitCore().getTextPane().setText(viewNoteText);
        htmlViewer.getEkitPanel().getEkitCore().getTextPane().setCaretPosition(0);
        htmlViewer.getEkitPanel().getEkitCore().refreshOnUpdate();
    }

    /**
     * ���o���`�M��data model
     * @see
     * @return
     */
    private ListModel getListModel() {
        DefaultListModel lm = new DefaultListModel();

        Iterator it = chapterOrderArray.iterator();
        //Iterator it = hm.keySet().iterator();
        while (it.hasNext()) {
            lm.addElement(it.next());
        }

        return lm;
    }
    
    public void changePersonIncharge(String strDocCode, String strDocName)
    {
    	noteEkitPanel.getEkitPanel().getEkitCore().setUpdateFlag(true);
    	
    	if("C".equals(currentVO.getRowStatus()))
    	{
    		currentVO.setRowStatus("C");
    	}
    	else
    	{
    		currentVO.setRowStatus("U");
    		System.out.println("3");
    	}    	
    	
    	this.txtPersonInChargeCode.setText(strDocCode);
    	this.txtPersonInChargeName.setText(strDocName);
    }
    
    public void setValueFromDeptLOV(String strDeptCode, String strDeptChName)
    {    	    
    	this.deptCodeField.setText(strDeptCode);
    	this.lblDeptName.setText(strDeptChName);
    }
    
    public void setValueFromVsLOV(String strDocCode, String strDocName)
    {    	    
    	this.vsCodeField.setText(strDocCode);
    	this.lblVsName.setText(strDocName);
    }
    
    public void setValueFromStationLOV(String strBranchCode, String strSmplName)
    {    	    
    	this.branchCodeField.setText(strBranchCode);
    	this.lblStationName.setText(strSmplName);
    }
    
    /**
     * �̶ǤJ�d���]�w�U���`���
     * @see
     * @param noteTemplateString �ǤJ�d��
     * @param isAppend ��ƥ[�J�Ҧ�(�л\�βK�[)
     */
    public void setNoteTemplate(HashMap noteTemplateString, boolean isAppend) {
    	noteEkitPanel.getEkitPanel().getEkitCore().setUpdateFlag(true); //update by leon 2014-02-24:DITTO�]��ק�
    	
    	syncViewerWithTextPane();
        Iterator it = noteTemplateString.keySet().iterator();
        while (it.hasNext()) {
            Object key = it.next();
            if (hm.containsKey(key)) {                
                String noteStr = null;
                
                noteStr = noteTemplateString.get(key).toString();
                noteStr = StringUtil.fixPtagNewLine(noteStr);
                noteStr = StringUtil.fixInP(noteStr);
                noteStr = StringUtil.fixPre(noteStr);
                
                //update by leon 2012-05-17
                if(noteStr.indexOf("<p>") == -1 && noteStr.indexOf("<p style=\"margin-top: 0\">") == -1 )
                {                                    
                    noteStr = "<p>" + noteStr + "</p>";
                }                 
                //else
                //{
                    //noteStr = noteStr;
                //}                            
                
                //update by leon 2012-06-06
                noteStr = StringUtil.fixSpace("&nbsp;",noteStr);
                noteStr = StringUtil.fixSpace("&#160;",noteStr);
                
                if (isAppend)
                {                                    
                    String newStr = hm.get(key).toString() + noteStr;
                    hm.put(key, newStr);
                }
                else
                {
                    hm.remove(key);
                    hm.put(key, noteStr);
                }
            }
        }        
        
        noteEkitPanel.getEkitPanel().getEkitCore().setDocumentText(hm.get(jList1.getSelectedValue()).toString());            
    }

    /**
     * �]�w�f�����
     * @see
     */
    public void setEventTimeTextValue() {
        if (currentVO != null) {
            try {
                eventTimeTextField.setText(DateUtil.getDateString(currentVO.getReportDate(), "yyyy-MM-dd HH:mm"));
            } catch (Exception e) {
                MessageManager.showException(e.getMessage(), e);
                logger.debug(e.getMessage(), e);
            }
        }
    }
    
    public void setEventTimeTextValue(Timestamp date) {
        if (currentVO != null) {
            try {
                eventTimeTextField.setText(DateUtil.getDateString(date, "yyyy-MM-dd HH:mm"));
            } catch (Exception e) {
                MessageManager.showException(e.getMessage(), e);
                logger.debug(e.getMessage(), e);
            }
        }
    }
    
    /**
     * �]�w�B�m�W��
     */
    public void setExamOrderDescValue() {
        if (currentVO != null && "TreNote".equals(filterString)) {
            try {
                //Default Value
                treNoteComboBox.setSelectedItem(treNoteComboBox.getModel().getElementAt(0));
            
                for (int i=0; i<treNoteComboBox.getModel().getSize(); i++)
                {
                    if(treNoteComboBox.getModel().getElementAt(i).equals(currentVO.examOrderDesc))
                    {
                        treNoteComboBox.setSelectedItem(treNoteComboBox.getModel().getElementAt(i));
                        break;
                    }
                }
                
            } catch (Exception e) {
                MessageManager.showException(e.getMessage(), e);
                logger.error(e.getMessage(), e);
            }
        }
    }
    
    /**
     * �q�l�f��
     * @param actionType
     */
    private void doEmrSheet(String actionType) throws Exception
    {
        System.out.println("doEmrSheet BEGIN");
        System.out.println("actionType: " + actionType);
    	    
    	if("Batch".equals(actionType))
    	{
            try
            {
                //INSERT LOG                    
    		//�eñ��
    		Connection conn = null;
                conn = DBConnectionHelper.getInstance().getConnection(GlobalParameters.getInstance().getMySql_url(),GlobalParameters.getInstance().getMySql_usrid(),GlobalParameters.getInstance().getMySql_pwd());           
                MySeqGenerator sg = new MySeqGenerator(conn);
                String documentId = sg.genSeq("DocumentId");
                
    		ProgressNoteEmrSheet progressNoteSheet = new ProgressNoteEmrSheet();    			
        	progressNoteSheet.setEmrIpdEncounterVO(GlobalParameters.getInstance().getEmrIpdEncounterVO());        		
                EmrIpdEncounterVO aa = progressNoteSheet.getEmrIpdEncounterVO();
        	progressNoteSheet.setActionType(actionType);
        	progressNoteSheet.documentId = documentId;
        	progressNoteSheet.doProcess();                    
            }
            catch(Exception e)
            {
                System.out.println("doEmrSheet EX: " + e.toString());
            }    		
        }
    	else
    	{
            //�D�v��v���� �� �D�D�v��v �Ȧs 
	    if(("2".equals(currentVO.getReportStatusScid()) || ("1".equals(currentVO.getReportStatusScid()) && !("D1".equals(GlobalParameters.getInstance().getSecUserVO().getUserGroup())))))
	    {
                System.out.println("111");
                if("ProgressNote".equals(filterString))
	        {//�f�{�O��
                    System.out.println("doEmrSheet ProgressNote BEGIN");
	            ProgressNoteEmrSheet progressNoteSheet = new ProgressNoteEmrSheet();
	            progressNoteSheet.documentId = currentVO.getDocumentId();
	            progressNoteSheet.setEmrPatNoteVO(currentVO);
	            progressNoteSheet.setEmrIpdEncounterVO(GlobalParameters.getInstance().getEmrIpdEncounterVO());
	            progressNoteSheet.setChapterDataMap(hm);
	            progressNoteSheet.setChapterList(emrNoteChapterData);
	            //progressNoteSheet.setReport(new ProgressNoteReport(0));
	            progressNoteSheet.setReport(new ProgressNoteReport(0,currentVO,true));                
	            progressNoteSheet.setActionType(actionType);                
	            progressNoteSheet.doProcess();
	            System.out.println("doEmrSheet ProgressNote END");
                }
	        else if("AdminNote".equals(filterString))
	        {//�J�|�K�n
                    System.out.println("doEmrSheet AdminNote");
	            AdminNoteEmrSheet adminNoteEmrSheet = new AdminNoteEmrSheet();
	            adminNoteEmrSheet.documentId = currentVO.getDocumentId();
	            adminNoteEmrSheet.setEmrPatNoteVO(currentVO);
	            adminNoteEmrSheet.setEmrIpdEncounterVO(GlobalParameters.getInstance().getEmrIpdEncounterVO());
	            adminNoteEmrSheet.setChapterDataMap(hm);
	            adminNoteEmrSheet.setChapterList(emrNoteChapterData);
	            //adminNoteEmrSheet.setReport(new AdminNoteReport());
	            adminNoteEmrSheet.setReport(new AdminNoteReport(currentVO,true));
	            adminNoteEmrSheet.setActionType(actionType);
	                
	            adminNoteEmrSheet.doProcess();
	            System.out.println("doEmrSheet AdminNote END");
                }
	        else if("DischgNote".equals(filterString))
	        {//�X�|�f�K
                    System.out.println("doEmrSheet DischgNote");
	           
	            //20130726 Amanda add.    IER-367 �X�|�K�n�W��HcaEmr�q�l�f��.PDF
	            DischgNoteEmrPdfSheet dischgNotePdfSheet = new DischgNoteEmrPdfSheet();
	            dischgNotePdfSheet.documentId = currentVO.getDocumentId();
	            dischgNotePdfSheet.setEmrPatNoteVO(currentVO);
	            dischgNotePdfSheet.setEmrIpdEncounterVO(GlobalParameters.getInstance().getEmrIpdEncounterVO());
	            dischgNotePdfSheet.setChapterDataMap(hm);
	            dischgNotePdfSheet.setChapterList(emrNoteChapterData);
	            //dischgNotePdfSheet.setReport(new DisChgReport());
	            dischgNotePdfSheet.setReport(new DisChgReport(currentVO, true));                
	            dischgNotePdfSheet.setActionType(actionType);
	                
                    System.out.println("�ϢϢ�");
                    dischgNotePdfSheet.doProcess();
	            System.out.println("doEmrSheet DischgNote END");
                }
	        //update by leon 2014-07-24:��N������
	        else if("OpNote".equals(filterString))
	        {
                    System.out.println("doEmrSheet OrReport");
                                    
                    //update by leon 2015-06-12: ��N�����楼�������eñ
                    OrReport orReport = new OrReport(currentVO.getSheetXmlMeta());
                    if("Y".equals(orReport.getOrVo().feeOpFlag))
                    {
                        OpNoteEmrPdfSheet opNoteEmrPdfSheet = new OpNoteEmrPdfSheet();
                        opNoteEmrPdfSheet.documentId = currentVO.getDocumentId();
                        opNoteEmrPdfSheet.setEmrPatNoteVO(currentVO);
                                            
                        String tempEncounterNo = currentVO.getEncounterNo();
                        if(tempEncounterNo.startsWith("I"))
                        {
                            opNoteEmrPdfSheet.setEmrIpdEncounterVO(GlobalParameters.getInstance().getEmrIpdEncounterVO());
                        }
                        else
                        {
                            //�d��OER_PATBAS                            
                            Statement stmt = null;
                            ResultSet rs = null;
                            Connection conn;        
                            
                            try
                            {               
                                conn = DBConnectionHelper.getInstance().getMySqlConnection();
                                stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                                                
                                StringBuffer sql = new StringBuffer();
                                sql.append(" SELECT * FROM emr.oer_patbas a WHERE a.encounter_no = '"+ tempEncounterNo +"' ");            
                                    
                                rs = stmt.executeQuery(sql.toString());            
                                if(rs.next())
                                {                   
                                    EmrIpdEncounterVO tempEmrIpdEncounterVO = new EmrIpdEncounterVO();
                                    tempEmrIpdEncounterVO.setEncounterNo(rs.getString("encounter_no"));
                                    tempEmrIpdEncounterVO.setAdmitDate(rs.getDate("opd_date"));
                                    tempEmrIpdEncounterVO.setChartNo(rs.getString("chart_no"));
                                    tempEmrIpdEncounterVO.setIdNo(rs.getString("id_no"));
                                    tempEmrIpdEncounterVO.setPatName(rs.getString("pat_name"));
                                    tempEmrIpdEncounterVO.setBedNo(rs.getString("bed_no"));
                                    tempEmrIpdEncounterVO.setDeptCode(rs.getString("dept_code"));
                                    tempEmrIpdEncounterVO.setDeptName(rs.getString("dept_name"));
                                    tempEmrIpdEncounterVO.setVsCode(rs.getString("doc_code"));
                                    tempEmrIpdEncounterVO.setVsName(rs.getString("doc_name"));
                                    opNoteEmrPdfSheet.setEmrIpdEncounterVO(tempEmrIpdEncounterVO);
                                }
                            }
                            catch(Exception e)
                            {
                                JOptionPane.showMessageDialog(this, "getOerPatbas Exception: " + e.toString());
                            }
                            finally
                            {
                                DBConnectionHelper.getInstance().cleanRsAndStmt(rs,stmt);
                            }
                        }                                    
                        
                        opNoteEmrPdfSheet.setChapterDataMap(hm);
                        opNoteEmrPdfSheet.setChapterList(emrNoteChapterData);                
                        opNoteEmrPdfSheet.setReport(orReport);                
                        opNoteEmrPdfSheet.setActionType(actionType);                
                        opNoteEmrPdfSheet.doProcess();
                    }	
	            System.out.println("doEmrSheet OrReport END");
                }
            }
    	}
    	
    	System.out.println("doEmrSheet END");
    }
    
    private void refreshFormTitle()
    {
        JFrame _jframe = sourceFrame;
    	if(_jframe == null)
        {
            _jframe = (JFrame)SwingUtilities.getAncestorOfClass(JFrame.class, this);
        }
        
    	String title = "";
    	for(Iterator<Map.Entry<String, String>> it = noteFormTitleHM.entrySet().iterator();it.hasNext();)
    	{
            Map.Entry<String, String> me = it.next();
            title += me.getValue() + " ";
    	}
    	
        _jframe.setTitle(title);
    }
    
    public void setJList1(JList jList1)
    {
        this.jList1 = jList1;
    }

    public JList getJList1()
    {
        return jList1;
    }
    
    public TreeMap<String, String> getFormTitleHM()
    {
        return noteFormTitleHM;
    }
	
    public void setFormTitleHM(TreeMap<String, String> setFormTitleHM)
    {
        this.noteFormTitleHM = setFormTitleHM;		
        this.refreshFormTitle();
    }    		

//***funtion list*************************************************************//
/**
 * changeTextPanel:����TAB���椧�ʧ@
 * checkOutDocument:����currentVO���s���v��
 * checkInDocument:����currentVO���s���v��
 * getIPAddress:���oIP��}
 * getDutyDocInfo:���oICU��Z��v
 * getCommNeedFlagByDate:�]�ַ��PROGRESS NOTE�O�_�ݭn�QCOMMAND
 * getRptdbFlag:���o�O�_�eñ�|��ñ��FLAG
 * limitsOfSave_2:�������]��
 * limitsOfSave_1:�Ȧs���]��
 * tempSave:�Ȧs
 * rollBack:�_��
 * */
//***funtion list*************************************************************//
 
    /**
     * ���ocurrentVO
     * */
    public EmrPatNoteVO getCurrentVO(long documentId)
    {
        System.out.println("[getCurrentVO][Begin]");
        
        EmrPatNoteVO vo = new EmrPatNoteVO();
        
        //�إ�JDBC�s�u
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn;        
        
        try
        {               
            conn = DBConnectionHelper.getInstance().getMySqlConnection();
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);                            
            StringBuffer sql = new StringBuffer();
            sql.append(" SELECT * FROM emr.emr_pat_note a WHERE a.id = "+ documentId +" ");
                           
            rs = stmt.executeQuery(sql.toString());            
            ArrayList list = EmrPatNoteDAO.getInstance().createVO(rs);
            
            vo = (EmrPatNoteVO)list.get(0);                            
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(this, "getCurrentVO Exception: " + e.toString());
        }
        finally
        {
            DBConnectionHelper.getInstance().cleanRsAndStmt(rs,stmt);           
        }
        
        System.out.println("[getCurrentVO][End]");
        return vo;
    } 
    
    /**
     * ���U�_����s���椧�ʧ@
     * */
    public void rollBack()
    {
        noteEkitPanel.setDataHasChangedStatus(false);        
        
        if(currentVO != null)
        {
            FunctionUtil functionUtil = new FunctionUtil();        
            String editByCode = functionUtil.getEditByCode(currentVO.getId());        
            if(editByCode.equals(GlobalParameters.getInstance().getSecUserVO().getUsername()))
            {
                this.checkInDocument(); //update by leon 2015-04-02: ����currentVO���s���v��
            }
                    
            currentVO = null;
        }
        
        this.setEkitCoreDocumentId(null);
        this.loadPatNoteData();
                
        if("loadEmrPatList".equalsIgnoreCase(loadEmrPatListF))
        {
            this.loadEmrPatList();
        }
        else if("loadEmrPatList1".equalsIgnoreCase(loadEmrPatListF))
        {
            this.loadEmrPatList1();
        }
        else if("loadEmrPatList2".equalsIgnoreCase(loadEmrPatListF))
        {
            this.loadEmrPatList2();
        }
        else if("loadEmrPatList3".equalsIgnoreCase(loadEmrPatListF))
        {
            this.loadEmrPatList3();
        }
        else
        {
            this.loadEmrPatList();
        }
        
        this.initNoteTextData();
        this.setEventTimeTextValue();
        this.setExamOrderDescValue();
        jideTabbedPane.setSelectedIndex(1);
        if(noteEkitPanel.isEkitCoreEditable() == false)
        {
            noteEkitPanel.setEkitCoreEditable(true);
        }
    }
    
    /**
     * CHECK IN DOCUMENT
     * */
    public void checkInDocument()
    {
        PreparedStatement stmt = null;
   
        try
        {
            Connection conn = DBConnectionHelper.getInstance().getMySqlConnection();
      
            String updateSQL = " UPDATE emr_pat_note SET  edit_by_code = ?, edit_by_name = ? WHERE id = ? ";
            stmt = conn.prepareStatement(updateSQL);
      
            stmt.setString(1,"");
            stmt.setString(2,"");
            stmt.setLong(3,currentVO.getId());
            stmt.execute();
        }
        catch(Exception e)
        {            
            MessageManager.showException(e.getMessage(),e);
        }
        finally
        {
            if(stmt != null)
            {
                try
                {
                    stmt.close();
                    stmt = null;
                }
                catch (SQLException ex)
                {
                    MessageManager.showException(ex.getMessage(),ex);
                    //logger.debug(ex.getMessage(),ex);
                }
            }
        }
    }
    
    /**
     * tab�����e���ʧ@
     * */
    public void changeTextPanel(int status)
    {
        //�f�w�M��
        if(status == 0)
        {
            if("loadEmrPatList".equalsIgnoreCase(loadEmrPatListF))
            {
                loadEmrPatList();
            }
            else if("loadEmrPatList1".equalsIgnoreCase(loadEmrPatListF))
            {
                loadEmrPatList1();
            }
            else if("loadEmrPatList2".equalsIgnoreCase(loadEmrPatListF))
            {
                loadEmrPatList2();
            }
            else if("loadEmrPatList3".equalsIgnoreCase(loadEmrPatListF))
            {
                loadEmrPatList3();
            }
            else
            {
                loadEmrPatList();
            }
                        
            ((CardLayout) jPanel2.getLayout()).show(jPanel2, "blankPanel");
        }
        //�f�����
        else if (status == 1)
        {  
            syncTextPaneWithSourcePane();
            syncViewerWithTextPane();
            saveEditorText2Row();
            ((CardLayout) jPanel2.getLayout()).show(jPanel2, "htmlViewer");
        }
        //���`����
        else if (status == 2)
        {
            //�۰ʷs�W
            if(currentVO == null)
            {
                if (addBtn.isEnabled())
                {
                    addBtn.doClick();
                }
                //��N���i�s�W
                else
                {
                    return;
                }
            }
            
            //���sŪ���f�w��ƪ����ܼ�
            EmrNoteCategoryVO vo = (EmrNoteCategoryVO)((HCComboBoxModel)noteCategoryComboBox.getModel()).getVO();            
            //�������ҫ�A�w�]��ܲĤ@�ӳ��`
         
            //���{�O�������쳹�`���ު��s��Ҧ�, �w�]���оǷN�����`
            if("ProgressNote".equals(filterString))
            {
                jList1.setSelectedIndex(5);
            }
             
            //�Y���ŭ�,�i��w�]HTML�B�z
            if(!StringUtil.isBlank(hm.get(jList1.getSelectedValue())))
            {
                noteEkitPanel.getEkitPanel().getEkitCore().getTextPane().setText(hm.get(jList1.getSelectedValue()).toString());
            }
            else
            {
                //update by leon 2012-05-09:�ץ��Y�|����J���󤺮e�Y�ϥίf�w��ƥ\��δ��J�Ϥ��L�k���`��ܤ��e
                noteEkitPanel.getEkitPanel().getEkitCore().getTextPane().setText("<p></p>");
            }
         
            syncTextPaneWithSourcePane();
         
            //������ñ�ɱa�J��ñ����v��T
            txtPersonInChargeName.setText(currentVO.getPersonInChargeName());
            txtPersonInChargeCode.setText(currentVO.getPersonInCharge());
         
            oldSeledtedItem = jList1.getSelectedValue().toString();
            oldSeledtedIndex = jList1.getSelectedIndex();
            noteDataObjectPanel.loadDataobjectData(vo.getId().toString(), oldSeledtedItem);
            noteDataObjectPanel.setNoteCategory(filterString);
            //�N���`�W����ܦb�s��Ϫ�TITLE�W
            outsideNoteEkitPanel.setBorder(BorderFactory.createTitledBorder(jList1.getSelectedValue().toString()));
         
            ((CardLayout) jPanel2.getLayout()).show(jPanel2, "noteEkitPanel");
         
            //update by leon 2015-03-30: �]�w�X�K���X�|�E�_���`��Ū, �Ȯɵ���
            setDischgDiagReadOnly();
         
            //update by leon 2015-03-30: CHECK OUT THIS DOCUMENT
            System.out.println("1");
            this.checkOutDocument();
        }
    }
  
    /**
     * CHECK OUT DOCUMENT
     * */
    public void checkOutDocument()
    {
        //System.out.println("checkOutDocument 1");
        PreparedStatement stmt = null;
      
        try
        {
            Connection conn = DBConnectionHelper.getInstance().getMySqlConnection();
         
            String updateSQL = " UPDATE emr_pat_note SET  edit_by_code = ?, edit_by_name = ? WHERE id = ? ";
            //System.out.println(updateSQL);
            stmt = conn.prepareStatement(updateSQL);
            
            stmt.setString(1,GlobalParameters.getInstance().getSecUserVO().getUsername());
            stmt.setString(2,GlobalParameters.getInstance().getSecUserVO().getUserRealName());
            stmt.setLong(3,currentVO.getId());
            
            //System.out.println(GlobalParameters.getInstance().getSecUserVO().getUsername());
            //System.out.println(GlobalParameters.getInstance().getSecUserVO().getUserRealName());
            //System.out.println(currentVO.getId());
            
            stmt.execute();
            
            //System.out.println("checkOutDocument 2");
        }
        catch(Exception e)
        {            
            MessageManager.showException(e.getMessage(),e);
        }
        finally
        {
            if(stmt != null)
            {
                try
                {
                    stmt.close();
                    stmt = null;
                }
                catch (SQLException ex)
                {
                    MessageManager.showException(ex.getMessage(),ex);
                    //logger.debug(ex.getMessage(),ex);
                }
            }
        }
    }
 
    /**
     * ���o����{���q��IP��}
     * */
    private String getIPAddress()
    {
        String ip = null;
        try
        {
            InetAddress addr = InetAddress.getLocalHost();
            ip = addr.getHostAddress();
        }
        catch(UnknownHostException e)
        {
            MessageManager.showException(e.getMessage(), e);
        }

        return ip;
    }
 
    /**
     * ���oICU��Z��v
     * */
    public HashMap getDutyDocInfo(String encounterNo)
    {
        System.out.println("[getDutyDocInfo][Begin][encounterNo: "+ encounterNo +"]");
    	
	HashMap dutyDocInfo = new HashMap();
    	
        //�إ�JDBC�s�u
    	Statement stmt = null;
        ResultSet rs = null;
        Connection conn;        
        
        try
        {        	
            conn = DBConnectionHelper.getInstance().getMySqlConnection();
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            	            
            StringBuffer sql = new StringBuffer();
            sql.append(" SELECT a.duty_doc_code,a.duty_doc_name,a.vs_code,a.vs_name FROM emr.emr_ipd_encounter a WHERE a.encounter_no = '"+ encounterNo +"' ");            
                
            rs = stmt.executeQuery(sql.toString());
            if(rs.next())
            {            	
                if(rs.getString("duty_doc_code") != "" && rs.getString("duty_doc_code") != null)
                {
                    dutyDocInfo.put("doc_code", rs.getString("duty_doc_code"));
                    dutyDocInfo.put("doc_name", rs.getString("duty_doc_name"));
                }
                else
                {
                    dutyDocInfo.put("doc_code", rs.getString("vs_code"));
                    dutyDocInfo.put("doc_name", rs.getString("vs_name"));
                }
            }        	        	        	                     
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(this, "getDutyDocInfo Exception: " + e.toString());
        }
        finally
        {
            DBConnectionHelper.getInstance().cleanRsAndStmt(rs,stmt);        	
        }
        
        System.out.println("[getDutyDocInfo][END][encounterNo: "+ encounterNo +"]");
        return dutyDocInfo;
    }
	    
    /**
     * �P�_���O�_�ݭn�QCOMMENT
     * */
    public boolean getCommNeedFlagByDate(String encounterNo, String reportDate)
    {
    	System.out.println("[getCommNeedFlagByDate][Begin][encounterNo: "+ encounterNo +"][reportDate: "+ reportDate +"]");
    	
    	boolean bolCommNeedFlag = true; //��ѬO�_�ݭnCOMMENT�A�w�]���ݭn
    	
        //�إ�JDBC�s�u
    	Statement stmt = null;
        ResultSet rs = null;
        Connection conn;
        
        try
        {        	
            conn = DBConnectionHelper.getInstance().getMySqlConnection();
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            	
            //�d�߷��w�QCOMMENT���f���ƶq
            StringBuffer sql = new StringBuffer();
            sql.append(" SELECT ");
            sql.append("     COUNT(1)AS commNeedFlag ");
            sql.append(" FROM ");
            sql.append("     emr.emr_pat_note a ");
            sql.append(" WHERE ");
            sql.append("     a.encounter_no = '"+ encounterNo +"' ");
            sql.append("     AND a.report_subtype_scid = 'progressnote' ");            
            sql.append("     AND a.report_date >= to_date('"+ reportDate +" 00:00:00', 'yyyy-mm-dd hh24:mi:ss') ");
            sql.append("     AND a.report_date <= to_date('"+ reportDate +" 23:59:59', 'yyyy-mm-dd hh24:mi:ss') ");
            sql.append("     AND a.comm_need_flag = 'N' ");
                
            rs = stmt.executeQuery(sql.toString());
            if(rs.next())
            {
            	//�Y���w���QCOMMENT�L������
                if(rs.getInt("commNeedFlag") > 0)
                {
                    //��骺��L�f�����ݦA�QCOMMENT
                    bolCommNeedFlag = false;
                }
            }        	        	        	                     
        }
        catch(Exception e)
        {            
            JOptionPane.showMessageDialog(this, "getCommNeedFlagByDate Exception: " + e.toString());
        }
        finally
        {
            DBConnectionHelper.getInstance().cleanRsAndStmt(rs,stmt);        	
        }
        
        System.out.println("[getCommNeedFlagByDate][End][encounterNo: "+ encounterNo +"][reportDate: "+ reportDate +"]");
        return bolCommNeedFlag;
    }
        
    /**
     * �P�_RPTDB�O�_�s��
     * */
    public boolean getRptdbFlag()
    {
        //System.out.println("[getRptdbFlag][Begin]");
    	
    	boolean result = true;
    	
        //�إ�JDBC�s�u
    	Statement stmt = null;
        ResultSet rs = null;
        Connection conn;        
        
        try
        {        	            
            conn = DBConnectionHelper.getInstance().getMySqlConnection();
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);            	           
            StringBuffer sql = new StringBuffer();
            sql.append(" SELECT nvl(a.code_desc,'Y')AS code_desc FROM com.com_codemst a WHERE a.code_type = 'RPTDB_FLAG' ");
                           
            rs = stmt.executeQuery(sql.toString());
            if(rs.next())
            {
                if("N".equals(rs.getString("code_desc")))
                {
                	result = false;
                }
            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(this, "getRptdbFlag Exception: " + e.toString());
        }
        finally
        {
            DBConnectionHelper.getInstance().cleanRsAndStmt(rs,stmt);        	
        }
        
        //System.out.println("[result]" + result);
        //System.out.println("[getRptdbFlag][End]");
        return result;
    }
    
    /**
     * �������]��
     * */
    public boolean limitsOfSave_2()
    {
        boolean rtnLimistFlag = true;
     
        //�]��1: �D�D�v��v���i�����f��
        if(!"D1".equals(GlobalParameters.getInstance().getSecUserVO().getUserGroup()))
        {
            String showMsg = "�D�D�v��v�A�ȯ�ϥΡy�Ȧs�s�z�i��s��!";
            JOptionPane.showMessageDialog(this, showMsg);
            rtnLimistFlag = false;
        }
     
        //�]��2: �X�|�K�n�����n�f�H�����A����v���\�X�|�~�i�H����(�T�O�f���W���X�|��������ŭ�)
        if(rtnLimistFlag && "DischgNote".equals(filterString))
        {
            String _patState = GlobalParameters.getInstance().getEmrIpdEncounterVO().getPatState();
            String sql = "select * from emr_ipd_encounter where encounter_no = '" + GlobalParameters.getInstance().getEmrIpdEncounterVO().getEncounterNo() + "'";
             
            try
            {
                ArrayList<?> list = EmrIpdEncounterDAO.getInstance().findAllBySql(sql);
             
                if(list.size() > 0)
                {
                    _patState = ((EmrIpdEncounterVO)list.get(0)).getPatState();
                }
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(this, e.toString());
            }
             
            if("D".equals(currentVO.getReportThirdTypeScid()))
            {//�X�K
                if("A".equals(_patState))
                {//�b�|��
                    String showMsg = "�X�|�K�n�ݯf�w���A����v���\�X�|�H��~�i����(�ثe���A:�b�|��)";
                    JOptionPane.showMessageDialog(this, showMsg);
                    rtnLimistFlag = false;
                }
            }                                       
        }
     
        //�]��3: ���{�O���ݦܤ֨��COMMENT�@��
        if(rtnLimistFlag)
        {                       
            //�w�]�����ݭnCOMMENT
            boolean runSetCommNeedFlag = false;
             
            //�P�_�O�_��progressnote            
            if("progressnote".equals(currentVO.getReportSubtypeScid()))     
            {                               
                //update by leon 2014-06-17: �D�v��v�s�W�L�k��������BUG�ץ�
                try
                {
                    currentVO.setReportDate(new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(eventTimeTextField.getText()).getTime())); //���i���
                }
                catch(Exception e)
                {
                    String showMsg = "�f��������i����";
                    JOptionPane.showMessageDialog(this, showMsg);
                    rtnLimistFlag = false;
                    return rtnLimistFlag;
                }                                                       
                     
                Calendar cal = Calendar.getInstance();
                cal.setTime(currentVO.getReportDate());
                cal.add(Calendar.DATE, -1);
             
                String todReportDateStr = new SimpleDateFormat("yyyy-MM-dd").format(currentVO.getReportDate());
                String yesReportDateStr = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());                                                                                             
                String admitDate = new SimpleDateFormat("yyyy-MM-dd").format(GlobalParameters.getInstance().getEmrIpdEncounterVO().getAdmitDate());
                     
                if(todReportDateStr.equals(admitDate))
                {
                    /*
                    String showMsg = "���餣�ΧP�_�A�i�s��";
                    JOptionPane.showMessageDialog(this, showMsg);
                    */
                    if(noteEkitPanel.getDataHasChangedStatus())
                    {
                        currentVO.setCommNeedFlag("N");
                    }
                }
                else
                {
                    //�Q�ѻݭnCOMMENT
                    if(getCommNeedFlagByDate(currentVO.getEncounterNo(),yesReportDateStr))
                    {
                        //���ѻ�COMMENT
                        if(getCommNeedFlagByDate(currentVO.getEncounterNo(),todReportDateStr))
                        {
                            //�������ʤ��e�A���i����
                            if(!noteEkitPanel.getDataHasChangedStatus())
                            {
                                String showMsg = "�ܤ֨�Ѽ��g�@��VS COMMENT�C";
                                JOptionPane.showMessageDialog(this, showMsg);
                                rtnLimistFlag = false;
                            }
                            //�����ʤ��e�A�i����
                            else
                            {                                                       
                                /*
                                String showMsg = "�����ʤ��e�A�i�s��";
                                JOptionPane.showMessageDialog(this, showMsg);
                                */
                                currentVO.setCommNeedFlag("N");                                               }
                            }
                        //���Ѥw��COMMENT�L�A�i�s��
                        else
                        {                                               
                            /*
                            String showMsg = "���Ѥw��COMMENT�L�A�i�s��";
                            JOptionPane.showMessageDialog(this, showMsg);
                            */
                            if(noteEkitPanel.getDataHasChangedStatus())
                            {
                                currentVO.setCommNeedFlag("N");
                            }
                        }
                    }
                    //�Q�ѤwCOMMENT�A���Ѥ��ΧP�_
                    else
                    {                                       
                        /*
                        String showMsg = "�Q�ѤwCOMMENT�A���Ѥ��ΧP�_";
                        JOptionPane.showMessageDialog(this, showMsg);
                        */
                        if(noteEkitPanel.getDataHasChangedStatus())
                        {
                            currentVO.setCommNeedFlag("N");
                        }
                    }
                }                               
            }
        }
     
        //�]��4: �X�K�u��s�@��
        if(rtnLimistFlag)
        {                                       
            if("dischgnote".equals(currentVO.getReportSubtypeScid()))
            {                                       
                if("D".equals(currentVO.getReportThirdTypeScid()))
                {                                               
                    int intDischgnoteCount = 0;
                    StringBuffer queryEmrPatNoteSQL = new StringBuffer();
                    ArrayList<Map> list;
                                                                                                 
                    //�s�W
                    if("C".equals(currentVO.getRowStatus()))
                    {
                        queryEmrPatNoteSQL.append(" SELECT ");
                        queryEmrPatNoteSQL.append("     * ");
                        queryEmrPatNoteSQL.append(" FROM ");
                        queryEmrPatNoteSQL.append("     emr.emr_pat_note a ");
                        queryEmrPatNoteSQL.append(" WHERE ");
                        queryEmrPatNoteSQL.append("     a.encounter_no = '"+ GlobalParameters.getInstance().getEmrIpdEncounterVO().getEncounterNo() +"' ");
                        queryEmrPatNoteSQL.append("     AND a.report_subtype_scid = 'dischgnote' ");
                        queryEmrPatNoteSQL.append("     AND a.report_third_type_scid = 'D' ");
                                                                                                                                                 
                        try
                        {
                            list = EmrPatNoteDAO.getInstance().findAllBySql(queryEmrPatNoteSQL.toString());                                                         
                            intDischgnoteCount = list.size();                                                                                                                                       
                        }
                        catch(Exception e)
                        {
                            String showMsg = "Exception: " + e.toString();
                            JOptionPane.showMessageDialog(this, showMsg);
                        }
                    }                        
                    else
                    {                                                                               
                        queryEmrPatNoteSQL.append(" SELECT ");
                        queryEmrPatNoteSQL.append("     * ");
                        queryEmrPatNoteSQL.append(" FROM ");
                        queryEmrPatNoteSQL.append("     emr.emr_pat_note a ");
                        queryEmrPatNoteSQL.append(" WHERE ");
                        queryEmrPatNoteSQL.append("     a.encounter_no = '"+ GlobalParameters.getInstance().getEmrIpdEncounterVO().getEncounterNo() +"' ");
                        queryEmrPatNoteSQL.append("     AND a.report_subtype_scid = 'dischgnote' ");
                        queryEmrPatNoteSQL.append("     AND a.report_third_type_scid = 'D' ");
                        queryEmrPatNoteSQL.append("     AND a.id != "+ currentVO.getId() +" ");
                                                                                                                                                 
                        try
                        {                                                                                                                                               
                            list = EmrPatNoteDAO.getInstance().findAllBySql(queryEmrPatNoteSQL.toString());                                                                                                                
                            intDischgnoteCount = list.size();                                
                        }
                        catch(Exception e)
                        {
                            String showMsg = "Exception: " + e.toString();
                            JOptionPane.showMessageDialog(this, showMsg);
                        }
                    }
                         
                    if(intDischgnoteCount > 0)
                    {
                        String showMsg = "�@�ӯf�H�u�঳�@���X�K";
                        JOptionPane.showMessageDialog(this, showMsg);
                        rtnLimistFlag = false;
                    }
                }
            }
        }
        
        //�]��5:�p�G���QCHECK OUT, �n�J�̻ݬ��P�@�H�~�i�H����
        if(rtnLimistFlag)
        {
            FunctionUtil functionUtil = new FunctionUtil();
            String editByCode = functionUtil.getEditByCode(currentVO.getId());
            
            if(!("N".equals(editByCode)))
            {
                if(!(editByCode.equals(GlobalParameters.getInstance().getSecUserVO().getUsername())))
                {
                    String editByName = functionUtil.getEditByName(editByCode);
                    String showMsg = "�����f����ƥثe��<"+ editByName +">�s�褤";
                    JOptionPane.showMessageDialog(this, showMsg);
                    rtnLimistFlag = false;
                }                
            }
        }
                        
        //�]��6:�X�K�ΤJ�K, �U�������e���o���Ť~�i����        
        if(rtnLimistFlag)
        {
            //�X�K���Ϊ̤J�K
            if("D".equals(currentVO.getReportThirdTypeScid())
                || "C".equals(currentVO.getReportThirdTypeScid())
                || "adminnote".equals(currentVO.getReportSubtypeScid()))
            {                
                //���o�b�s��ϩ|���s�ɪ������
                String tmpDocumentText = getNewNoteString();                                                                            
                
                //�v�@���`�]��
                String showMsg = "";                
                Iterator it = hm.keySet().iterator();
                while (it.hasNext())
                {
                    //���`�W��
                    String key = it.next().toString();                    
                    
                    if(!"12.�оǷN��".equals(key) && !"12.��L".equals(key))
                    {
                        //���`�_�l��m
                        int tokenS = tmpDocumentText.indexOf("<"+ key +">");
                        tokenS = tmpDocumentText.indexOf(">",tokenS)+1;                    
                        
                        //���`������m
                        int tokenE = tmpDocumentText.indexOf("</"+ key +">");                    
                        
                        //���`���e
                        String tmpStr = tmpDocumentText.substring(tokenS, tokenE);
                                            
                        tmpStr = tmpStr.replaceAll("<.*?>", ""); //�h��HTML TAG
                        tmpStr = tmpStr.trim(); //�h���ť�
                        
                        //�Y����g���e�d�������\����
                        if(tmpStr.length()==0)
                        {
                            showMsg = showMsg + (key + "\n");
                            rtnLimistFlag = false;
                        }
                    }
                }
                
                if(rtnLimistFlag==false)                
                {
                    JOptionPane.showMessageDialog(this, "�U�C���`�����\����:\n" + showMsg);
                }                
            }
        }        
        
        //�]��7:��N����, �]�֤�N�O���楼�������i������N���i        
        if(rtnLimistFlag)
        {
            if("opnote".equals(currentVO.getReportSubtypeScid()))
            {
                OrReport orReport = new OrReport(currentVO.getSheetXmlMeta());
                if(!("Y".equals(orReport.getOrVo().feeOpFlag)))
                {
                    String showMsg = "��������N������A�~�i������N���i�A�Х��Ȧs";
                    JOptionPane.showMessageDialog(this, showMsg);
                    rtnLimistFlag = false;
                }
            }
        }
        
        return rtnLimistFlag;
    }
    
    /**
     * �Ȧs���]��
     * */
    public boolean limitsOfSave_1()
    {
        boolean rtnLimistFlag = true;
        
        //�]��1: �w�������f�����i�A�Ȧs
        if("2".equals(currentVO.getReportStatusScid()))
        {
            //update by leon 2014-02-25:�w�������i�A�ϥμȦs�A���ܫᤣ�n�M�Ÿ��
            //rollBack();
            String showMsg = "�w�������i�A�ϥμȦs�C";
            JOptionPane.showMessageDialog(this, showMsg);
            rtnLimistFlag = false;
        }
                                            
        //�]��2: �����ʤ��e�����\�Ȧs
        if(rtnLimistFlag)
        {
            if(noteEkitPanel.getDataHasChangedStatus())
            {
                //�D�v��v�wCOMMENT�A�M�v�Φ�|��v�A�h�ק�A���έ���COMMENT
                if("N".equals(currentVO.getCommNeedFlag()))
                {
                    currentVO.setCommNeedFlag("N");
                }
                //�D�v��v��COMMENT
                else
                {
                    //�M�v�Φ�|��v�Ȧs�A�̵M�ݭn�QCOMMENT
                    if(!"D1".equals(GlobalParameters.getInstance().getSecUserVO().getUserGroup()))
                    {
                        currentVO.setCommNeedFlag("Y");
                    }
                    //�D�v��v�Ȧs�A���O���wCOMMENT
                    else
                    {
                        currentVO.setCommNeedFlag("N");
                    }
                }
            }
            else
            {
                String showMsg = "�����ʥ��󤺮e�A�����\�Ȧs�C";
                JOptionPane.showMessageDialog(this, showMsg);
                rtnLimistFlag = false;
            }
        }
            
        //�]��3: �X�K�u��s�@��
        if(rtnLimistFlag)
        {
            if("dischgnote".equals(currentVO.getReportSubtypeScid()))
            {
                if("D".equals(currentVO.getReportThirdTypeScid()))
                {
                    int intDischgnoteCount = 0;
                    StringBuffer queryEmrPatNoteSQL = new StringBuffer();
                    ArrayList<Map> list;
                                    
                    //�s�W
                    if("C".equals(currentVO.getRowStatus()))
                    {                                       
                        queryEmrPatNoteSQL.append(" SELECT ");
                        queryEmrPatNoteSQL.append("     * ");
                        queryEmrPatNoteSQL.append(" FROM ");
                        queryEmrPatNoteSQL.append("     emr.emr_pat_note a ");
                        queryEmrPatNoteSQL.append(" WHERE ");
                        queryEmrPatNoteSQL.append("     a.encounter_no = '"+ GlobalParameters.getInstance().getEmrIpdEncounterVO().getEncounterNo() +"' ");
                        queryEmrPatNoteSQL.append("     AND a.report_subtype_scid = 'dischgnote' ");
                        queryEmrPatNoteSQL.append("     AND a.report_third_type_scid = 'D' ");
                                                                                                                                                            
                        try
                        {                            
                            list = EmrPatNoteDAO.getInstance().findAllBySql(queryEmrPatNoteSQL.toString());                                                                                                                
                            intDischgnoteCount = list.size();                            
                        }
                        catch(Exception e)
                        {
                            String showMsg = "Exception: " + e.toString();
                            JOptionPane.showMessageDialog(this, showMsg);
                        }
                    }                    
                    else
                    {
                        queryEmrPatNoteSQL.append(" SELECT ");
                        queryEmrPatNoteSQL.append("     * ");
                        queryEmrPatNoteSQL.append(" FROM ");
                        queryEmrPatNoteSQL.append("     emr.emr_pat_note a ");
                        queryEmrPatNoteSQL.append(" WHERE ");
                        queryEmrPatNoteSQL.append("     a.encounter_no = '"+ GlobalParameters.getInstance().getEmrIpdEncounterVO().getEncounterNo() +"' ");
                        queryEmrPatNoteSQL.append("     AND a.report_subtype_scid = 'dischgnote' ");
                        queryEmrPatNoteSQL.append("     AND a.report_third_type_scid = 'D' ");
                        queryEmrPatNoteSQL.append("     AND a.id != "+ currentVO.getId() +" ");
                                                                                                                                                            
                        try
                        {                            
                            list = EmrPatNoteDAO.getInstance().findAllBySql(queryEmrPatNoteSQL.toString());                                                                                                                
                            intDischgnoteCount = list.size();                            
                        }
                        catch(Exception e)
                        {
                            String showMsg = "Exception: " + e.toString();
                            JOptionPane.showMessageDialog(this, showMsg);
                        }
                    }
                                    
                    if(intDischgnoteCount > 0)
                    {
                        String showMsg = "�@�ӯf�H�u�঳�@���X�K";
                        JOptionPane.showMessageDialog(this, showMsg);
                        rtnLimistFlag = false;
                    }
                }
            }
        }
        
        //�]��4: �۰ʲ��ͤ��f�������\����        
        if(rtnLimistFlag)
        {
            if("012004-1".equals(currentVO.getCreatedBy()))
            {
                if(!currentVO.getReportDateStr().equals(eventTimeTextField.getText()))
                {
                    String showMsg = "�t�Ψ̳W�d�۰ʫإߤ����n�f���A���o�ܧ�f�����";
                    JOptionPane.showMessageDialog(this, showMsg);
                    rtnLimistFlag = false;
                }                
            }
        }
        
        if(rtnLimistFlag)
        {
            if("012004-1".equals(currentVO.getCreatedBy()))
            {
                if(currentVO.getReportSubtypeScid().equals("progressnote"))
                {                    
                    System.out.println(wkSummaryComboBox.getSelectedItem().toString());
                    System.out.println(currentVO.getReportThirdTypeCode());                    
                    if(!wkSummaryComboBox.getSelectedItem().toString().equalsIgnoreCase(currentVO.getReportThirdTypeCode()))
                    {
                        String showMsg = "�t�Ψ̳W�d�۰ʫإߤ����n�f���A���o�ܧ�f���l���O";
                        JOptionPane.showMessageDialog(this, showMsg);
                        rtnLimistFlag = false;
                    }                    
                }
                else if(currentVO.getReportSubtypeScid().equals("dischgnote"))
                {
                    /*
                    if(dischgNoteComboBox.getSelectedItem().toString().equalsIgnoreCase(currentVO.getReportThirdTypeCode()))
                    {
                        String showMsg = "�t�Ψ̳W�d�۰ʫإߤ����n�f���A���o�ܧ�f���l���O";
                        JOptionPane.showMessageDialog(this, showMsg);
                        rtnLimistFlag = false;
                    }
                    */
                }
            }
        }
                    
        return rtnLimistFlag;
    }
        
    /**
     * �Ȧs���s�ʧ@
     * */
    public void tempSave()
    {
        if (currentVO != null)
        {
            try
            {                
                //�s�ɫe�]��
                if(limitsOfSave(false) == false)
                {
                    return;
                }
            
                //�s�ɫe�]��2
                checkData2();
                
                //�ץ�
                fixDocumentText();
                    
                //�s��(rule:�@��������N���i�A�Ȧs)
                saveData("2".equals(currentVO.getReportStatusScid()) ? "2" : "1");
            
                setDischgDiagReadOnly();
                rollBack();
            }
            catch (Exception ex)
            {
                MessageManager.showException(ex.getMessage(), ex);                    
            }
        }
    }
    /**
     * �ɭ��E�_���@
     * */
    public void directToDiagPage()
    {
        System.out.println("[directToDiagPage][START]");                    
        StringBuffer strUrl = new StringBuffer();
        
        try
        {
            String strUser = GlobalParameters.getInstance().getLoginUserName();
            String strPassword = "";
            String strBranch = "";
            
            Statement stmt = null;
            ResultSet rs = null;
            Connection conn;        
            
            try
            {               
                conn = DBConnectionHelper.getInstance().getMySqlConnection();
                stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                                
                StringBuffer sql = new StringBuffer();
                sql.append(" SELECT sec_user.passwd,sec_user.branch_code FROM newsec.sec_user sec_user WHERE sec_user.username='"+ strUser +"' ");
                    
                rs = stmt.executeQuery(sql.toString());
                if(rs.next())
                {
                    strPassword = rs.getString("passwd");
                    strBranch = rs.getString("branch_code");
                }
            }
            catch(Exception ex)
            {
                System.out.println("directToDiagPage Exception: " + ex.toString());
                JOptionPane.showMessageDialog(this, "directToDiagPage Exception: " + ex.toString());
            }
            finally
            {
                DBConnectionHelper.getInstance().cleanRsAndStmt(rs,stmt);           
            }
            
            strUrl = new StringBuffer();
            //strUrl.append("rundll32 url.dll,FileProtocolHandler ");            
            
            String aEnvArg = GlobalParameters.getInstance().getEnvArg().toString();
            
            
            /**�շR*/
            //���վ�
            if("test".equalsIgnoreCase(aEnvArg))
            {
                strUrl.append("http://testhisapp:8180/PohaiServer/MainSocket.html");
            }
            //������
            else if("production".equalsIgnoreCase(aEnvArg))
            {                
                strUrl.append("http://hisapp:8080/PohaiServer/MainSocket.html");
            }            

            strUrl.append(("#user=" + strUser)); //�b��
            strUrl.append(("&password=" + strPassword)); //�K�X
            strUrl.append(("&branch=" + strBranch)); //�n�J�a�I EX:03D0
            strUrl.append("&loginType=IER");
            strUrl.append("&moduleId=IerMain");
            strUrl.append("&tabId=Ier2020");
            strUrl.append("&pageId=Ier2020Page");
            strUrl.append(("&strEncounterNo=" + GlobalParameters.getInstance().getEncounterNo())); //��|�Ǹ�
            strUrl.append(("&strChartNo=" + GlobalParameters.getInstance().getChartNo())); //�f����    
                        
            System.out.println(strUrl.toString());                                                
            //Chromium�}��
            Runtime.getRuntime().exec(new String[] {"C:\\Chromiumhis\\bin\\chrome.exe", strUrl.toString()});
            //��������
            //Runtime.getRuntime().exec(new String[] {"D:\\chrlauncher-win64-stable-codecs-sync\\bin\\chrome.exe", strUrl.toString()});
            System.out.println("[directToDiagPage][END]");
        }
        catch(Exception ex)
        {
            try
            {
                //32�줸IE�}��
                Runtime.getRuntime().exec(new String[] {"C:\\Program Files (x86)\\Internet Explorer\\iexplore.exe", strUrl.toString()});                
            }
            catch(Exception ee)
            {
                try
                {
                    //64�줸IE�}��
                    Runtime.getRuntime().exec(new String[] {"C:\\Program Files\\internet explorer\\iexplore.exe", strUrl.toString()});
                }
                catch(Exception eee)
                {
                    
                    System.out.println("directToDiagPage Exception: " + eee.toString());
                    JOptionPane.showMessageDialog(this, "directToDiagPage Exception: " + eee.toString());
                }                
            }                    
        }
    }
    
    /**
     * ���o�f�w���A
     * */
    public String getPatState()
    {
        String _patState = GlobalParameters.getInstance().getEmrIpdEncounterVO().getPatState();
        String sql = "select * from emr_ipd_encounter where encounter_no = '" + GlobalParameters.getInstance().getEmrIpdEncounterVO().getEncounterNo() + "'";        
          
        try
        {
            ArrayList<?> list = EmrIpdEncounterDAO.getInstance().findAllBySql(sql);
          
            if(list.size() > 0)
            {
                _patState = ((EmrIpdEncounterVO)list.get(0)).getPatState();
            }
         }
         catch(Exception e)
         {
             JOptionPane.showMessageDialog(this, e.toString());
         }
         
         return _patState;
    }    
    
    /**
     * �d�߶q�f���L����
     * */
    public int getEmrPatNoteErrlogCount()
    {
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn;      
        int errCount = 0;
        
        try
        {               
            conn = DBConnectionHelper.getInstance().getMySqlConnection();
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                                        
            StringBuffer sql = new StringBuffer();            
            sql.append(" SELECT COUNT(1)AS err_count FROM emr.emr_pat_note_errlog a WHERE a.encounter_no = '"+ GlobalParameters.getInstance().getEncounterNo() +"' ");            
                
            rs = stmt.executeQuery(sql.toString());
            if(rs.next())
            {
                errCount = rs.getInt("err_count");                
            }
            
            return errCount;
        }
        catch(Exception ex)
        {            
            showMessage("getEmrPatNoteErrlogCount Exception: " + ex.toString());
        }
        finally
        {
            DBConnectionHelper.getInstance().cleanRsAndStmt(rs,stmt);           
            return errCount;
        }
    }
    
    /**
     * �p�G�t�ε���OR
     * Document_No����             
     * �ӵ���Ƥw����
     * emr_pat_note_log >> count 0
     * �eñ
     * */
    
    public void batchReDoOpNoteEmrSheet()
    {
        Statement stmt = null;
        ResultSet emrPatNoteRs = null;
        Connection conn;        
        
        try
        {               
            conn = DBConnectionHelper.getInstance().getMySqlConnection();
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            
            StringBuffer emrPatNoteSQL = new StringBuffer();
            emrPatNoteSQL.append(" SELECT ");
            emrPatNoteSQL.append("    a.encounter_no ");
            emrPatNoteSQL.append("    ,a.chart_no ");
            emrPatNoteSQL.append("    ,a.document_number  ");
            emrPatNoteSQL.append(" FROM emr.emr_pat_note a ");
            emrPatNoteSQL.append(" WHERE a.report_subtype_scid = 'opnote' ");
            //emrPatNoteSQL.append("   AND a.encounter_no = 'E15102200067' ");
            //emrPatNoteSQL.append("   AND a.document_number = 'EOR-151000176' ");
            emrPatNoteSQL.append("   AND a.report_status_scid = '2' ");
            emrPatNoteSQL.append("   AND a.report_date >= (SYSDATE - 30) ");
            emrPatNoteSQL.append("   AND a.sheet_xml_meta LIKE'%feeOpFlag=''Y''%' ");
            //emrPatNoteSQL.append("   AND a.encounter_no LIKE'E%' ");
            emrPatNoteSQL.append("   AND (SELECT COUNT(1) ");
            emrPatNoteSQL.append("            FROM emr.emr_pat_note_log b ");
            emrPatNoteSQL.append("           WHERE b.encounter_no = a.encounter_no ");
            emrPatNoteSQL.append("             AND b.document_number = a.document_number ");
            emrPatNoteSQL.append("             AND b.report_status_scid = a.report_status_scid) = 0 ");
            emrPatNoteSQL.append(" ORDER BY a.encounter_no DESC ");
            emrPatNoteSQL.append("          ,a.chart_no ");
            emrPatNoteSQL.append("          ,a.document_number ");
                
            emrPatNoteRs = stmt.executeQuery(emrPatNoteSQL.toString());            
            while(emrPatNoteRs.next())
            {
                this.reDoOpNoteEmrSheet(emrPatNoteRs.getString("encounter_no"),"OR",emrPatNoteRs.getString("document_number"));
            }
            emrPatNoteRs.close();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(this, "getOerPatbas Exception: " + e.toString());
        }
        finally
        {
            DBConnectionHelper.getInstance().cleanRsAndStmt(emrPatNoteRs,stmt);
        }
    }
        
    /**
     * �������
     * */
    public void doDiffPdf()
    {
        System.out.println("[doDiffPdf][Start]");
        //���o�I�諸document id
        String documentId = currentVO.getDocumentId();
        
        Connection conn;
        Statement stmt = null;
        ResultSet rs = null;                                
        boolean checkFlag = true;
                
        try
        {               
            System.out.println("�d��hcaemr���o�D�v��v�̫᪩");
            //�d��hcaemr���o�D�v��v�̫᪩
            conn = DBConnectionHelper.getInstance().getMySqlConnection();            
            System.out.println("�d��hcaemr���o�D�v��v�̫᪩");
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);            
            System.out.println("�d��hcaemr���o�D�v��v�̫᪩");
                            
            StringBuffer sql = new StringBuffer();
            sql.append(" SELECT a.document_id ");
            sql.append("  FROM hcaemr.emr_general_sheet_worklist@rptdb a ");
            sql.append(" WHERE a.document_number = '"+ documentId +"' ");
            sql.append("   AND EXISTS (SELECT * ");
            sql.append("		  FROM com.com_docbas b ");
            sql.append("		 WHERE a.adviser_by_scid = b.doc_code ");
            sql.append("		   AND b.doc_degree IN ('1', '2')) ");            
            sql.append(" ORDER BY a.last_updated DESC ");                    
                
            System.out.println(sql.toString());
            rs = stmt.executeQuery(sql.toString());
            System.out.println("�d��hcaemr���o�D�v��v�̫᪩");
            if(rs.next())
            {                   
                System.out.println("�d��hcaemr���o�D�v��v�̫᪩");
                downFile(rs.getString("document_id"),"vs");
                System.out.println("�d��hcaemr���o�D�v��v�̫᪩");
            }
            else
            {
                checkFlag = false;
                JOptionPane.showMessageDialog(this, "�D�v��v�����g���󪩥�");                
            }
            
            System.out.println("�d��hcaemr���o�D�D�v��v�̫᪩");
            //�d��hcaemr���o�D�D�v��v�̫᪩
            sql = new StringBuffer();
            sql.append(" SELECT a.document_id ");
            sql.append("  FROM hcaemr.emr_general_sheet_worklist@rptdb a ");
            sql.append(" WHERE a.document_number = '"+ documentId +"' ");
            sql.append("   AND NOT EXISTS (SELECT * ");
            sql.append("                  FROM com.com_docbas b ");
            sql.append("                 WHERE a.adviser_by_scid = b.doc_code ");
            sql.append("                   AND b.doc_degree IN ('1', '2')) ");
            sql.append(" ORDER BY a.last_updated DESC ");
                
            rs = stmt.executeQuery(sql.toString());            
            if(rs.next())
            {                   
                downFile(rs.getString("document_id"),"apn");
            }
            else
            {
                checkFlag = false;
                JOptionPane.showMessageDialog(this, "�M�v/��|��v�����g���󪩥�");
            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(this, "getOerPatbas Exception: " + e.toString());
        }
        finally
        {
            DBConnectionHelper.getInstance().cleanRsAndStmt(rs,stmt);    
        }        
                
        //���2.X                
        if(checkFlag)
        {
            String aEnvArg = GlobalParameters.getInstance().getEnvArg().toString();
            
            
            /**�շR���վ�&������*/
            executeCommand("\\\\nt-svr2\\�ϥΪ̮ୱ���|\\�q�l�f�����\\diffpdf -w D:\\apn.pdf D:\\vs.pdf");
            
            
            /**�O�ٴ��վ�&�U����
            executeCommand("C:\\diffpdf -w C:\\apn.pdf C:\\vs.pdf");            
            */
        }        
        
        //���5.X
        //executeCommand("C:\\Program Files\\Qtrac\\DiffPDF\\diffpdf ");
        System.out.println("[doDiffPdf][End]");
    }
    
    /**
     * ����CMD
     * */
    public void executeCommand(String command)
    {
        Process p;
        try
        {            
            p = Runtime.getRuntime().exec(command);                            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
            
    public void downFile(String documentId,String newFileName)
    {
        System.out.println("[downFile][Start]");
        String url = "10.10.10.14";
        //String url = "10.2.3.14";
        String username = "hcaemr2";
        String password = "hcaemr2";
        String remotePath = "";
        String fileName = "";
        //String localPath = "C:\\Users\\test\\Desktop\\diffpdf\\";
        //String localPath = "C:\\Users\\test\\Desktop\\";
        String localPath = "D:\\";
                
        Connection conn;
        Statement stmt = null;
        ResultSet rs = null;                                
        String signStatus = "";
        
        try
        {               
            conn = DBConnectionHelper.getInstance().getMySqlConnection();
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                                
            /*�wñ����
            StringBuffer sql = new StringBuffer();
            sql.append(" SELECT ");
            sql.append("     a.document_folder ");
            sql.append("     ,a.document_name   ");
            sql.append(" FROM ");
            sql.append("     hcaemr.emr_file_path@rptdb a ");
            sql.append(" WHERE ");
            sql.append("     a.document_id = '"+ documentId +"' ");
                                                    
            rs = stmt.executeQuery(sql.toString());            
            if(rs.next())
            {                   
                remotePath = rs.getString("document_folder");
                fileName = rs.getString("document_name");                
            }
            */
            
            //��ñ����
            StringBuffer sql = new StringBuffer();
            sql.append(" SELECT ");
            sql.append("     a.external_file_url,a.sign_status,(CASE WHEN(a.date_created >= to_date('2016-06-01','yyyy-mm-dd'))THEN'NEW'ELSE'OLD'END)AS sign_type ");
            sql.append(" FROM ");
            sql.append("     hcaemr.emr_general_sheet_worklist@rptdb a ");
            sql.append(" WHERE ");
            sql.append("     a.document_id = '"+ documentId +"' ");
            
            //System.out.println(sql.toString());
                        
            rs = stmt.executeQuery(sql.toString());
            if(rs.next())
            {                   
                String aa = rs.getString("external_file_url");
                String bb[] = aa.split("/");
                String signType = rs.getString("sign_type");                            
                signStatus = rs.getString("sign_status");
                
                //��ñ
                if("N".equals(rs.getString("sign_status")))
                {
                    //FileServer;/sourceDocu/IER/0000000/20160204008476_20160204155832.PDF
                    remotePath = bb[1] +"/"+ bb[2] +"/"+ bb[3];
                    fileName = bb[4];
                }
                //�wñ
                else if("Y".equals(rs.getString("sign_status")))
                {
                    if("NEW".equals(signType))
                    {
                        //FileServer;xmlsign/2016/06/28/IER/0345612/IER_DISCHGNOTE_PDF_1160628289.pdf
                        remotePath = bb[0].split(";")[1] +"/"+ bb[1] +"/"+ bb[2]+"/"+ bb[3]+"/"+ bb[4]+"/"+ bb[5];                        
                        fileName = bb[6].replaceAll(".pdf",".xml");                        
                    }
                    else
                    {
                        //FileServer;xmlsign/2016/05/31/IER/IER_DISCHGNOTE_PDF_116053150.pdf
                        remotePath = bb[0].split(";")[1] +"/"+ bb[1] +"/"+ bb[2]+"/"+ bb[3]+"/"+ bb[4];
                        fileName = bb[5].replaceAll(".pdf",".xml");
                    }
                }
                //�Nñ
                else if("P".equals(rs.getString("sign_status"))||"D".equals(rs.getString("sign_status")))
                {
                      if("NEW".equals(signType))
                      {
                          //FileServer;xmlsign/2016/06/28/IER/0345612/IER_DISCHGNOTE_PDF_1160628289.pdf
                          remotePath = bb[0].split(";")[1] +"/"+ bb[1] +"/"+ bb[2]+"/"+ bb[3]+"/"+ bb[4]+"/"+ bb[5];                          
                          fileName = bb[6].replaceAll(".pdf",("-"+ rs.getString("sign_status") +".xml"));
                      }
                      else
                      {
                          //FileServer;xmlsign/2016/05/31/IER/IER_DISCHGNOTE_PDF_116053150.pdf
                          remotePath = bb[0].split(";")[1] +"/"+ bb[1] +"/"+ bb[2]+"/"+ bb[3]+"/"+ bb[4];
                          fileName = bb[5].replaceAll(".pdf",("-"+ rs.getString("sign_status") +".xml"));
                      }
                }
                //�Nñ
                else if("I".equals(rs.getString("sign_status")))
                {
                    //FileServer;xmlsign/Hcaemr/2017/07/28/Instead/IER/0855875/IER_PROGRESSNOTE_1170727852.pdf
                    remotePath = bb[0].split(";")[1] +"/"+ bb[1] +"/"+ bb[2]+"/"+ bb[3]+"/"+ bb[4]+"/"+ bb[5]+"/"+ bb[6]+"/"+ bb[7];
                    fileName = bb[8].replaceAll(".pdf",("-"+ rs.getString("sign_status") +".xml"));
                }
                                
                System.out.println("remotePath: " + remotePath);
                System.out.println("fileName: " + fileName);
            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(this, "downFile Exception: " + e.toString());
        }
        finally
        {
            DBConnectionHelper.getInstance().cleanRsAndStmt(rs,stmt);    
        }
                                
        try
        {            
            //���o�ɮ�
            byte[] data = getDataFromFtp(url,username,password, remotePath, fileName);            
            
            //�wñ������XML�ഫ��PDF
            String pdfContent = "";
            if(!"N".equals(signStatus))
            {
                Document _document = DocumentHelper.parseText(new String(data,"UTF-8").trim());
                Element _root = _document.getRootElement();
                pdfContent = _root.element("pdfContent").getText();                
            }
            
            //�t�sPDF            
            java.io.FileOutputStream out;
            out = new java.io.FileOutputStream(localPath + newFileName + ".pdf");
            
            if(!"N".equals(signStatus))
            {
                out.write(Base64Utils.decode(pdfContent));            
            }
            else
            {
                out.write(data);
            }
            
            out.close();
        }
        catch (Exception e)
        {  
            System.out.println("downFile Exception: " + e.toString());
        }
        finally
        {  
            
        }
        
        System.out.println("[downFile][End]");
    }
    
    public static byte[] getDataFromFtp(String host, String user, String password, String path, String fileName)
    {
        byte b[] = new byte[]{};
        FTPClient ftp = new FTPClient();

        try
        {
            ftp.setTimeout(180000);
            ftp.setRemoteHost(host);
            FTPMessageCollector listener = new FTPMessageCollector();
            ftp.setMessageListener(listener);
            ftp.connect();
            ftp.login(user, password);
            ftp.setConnectMode(FTPConnectMode.PASV);
            ftp.setType(FTPTransferType.BINARY);
            System.out.println("path :" + path);
            System.out.println("fileName :" + fileName);
            
            try
            {
                ftp.chdir("/");
                ftp.chdir(path);
            }
            catch (Exception e)
            {
                System.out.println("Occurs Exception during do chdir action : " + e.toString());
            }
            
            b = ftp.get(fileName);
            System.out.println("FTP Log:" + listener.getLog());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            try
            {
                if(ftp!=null && ftp.connected())
                {
                    ftp.quit();
                }
            }
            catch (Exception ex)
            {
                System.out.println("Occurs Exception during quit Ftp connection : " + ex.toString());
                ex.printStackTrace();
            }
        }
        
        return b;
    }
    
    public void reDoOpNoteEmrSheet(String tempEncounterNo,String tempSys,String tempDocumentNo)
    {
        try
        {
            //�p�G�t�ε���OR
            /*
            String tempEncounterNo = GlobalParameters.getInstance().getEncounterNo();
            String tempSys = GlobalParameters.getInstance().getSys();
            String tempDocumentNo = GlobalParameters.getInstance().getDocumentNo();
            */
            
            if("OR".equalsIgnoreCase(tempSys))
            {
                //Document_No����
                if(tempDocumentNo != null)
                {
                    //�ӵ���Ƥw����
                     StringBuffer sql = new StringBuffer();
                     sql.append(" SELECT ");                     
                     sql.append("     * ");
                     sql.append(" FROM ");
                     sql.append("     emr.emr_pat_note a ");
                     sql.append(" WHERE ");
                     sql.append("     a.encounter_no = '"+ tempEncounterNo +"' ");
                     sql.append("     AND a.document_number = '"+ tempDocumentNo +"' ");
                     sql.append("     AND a.report_status_scid = '2' ");                     
                     sql.append("     AND (SELECT ");
                     sql.append("              COUNT(1) ");
                     sql.append("          FROM ");
                     sql.append("              emr.emr_pat_note_log b ");
                     sql.append("          WHERE ");
                     sql.append("              b.encounter_no = a.encounter_no ");
                     sql.append("              AND b.document_number = a.document_number ");
                     sql.append("              AND b.report_status_scid = a.report_status_scid) = 0 ");
                                          
                     ArrayList list = EmrPatNoteDAO.getInstance().findAllBySql(sql.toString());                                                        
                     if (list.size() > 0)
                     {                        
                        EmrPatNoteVO vo = (EmrPatNoteVO) list.get(0);
                        
                        OrReport orReport = new OrReport(vo.getSheetXmlMeta());
                        if("Y".equals(orReport.getOrVo().feeOpFlag))
                        {
                            EmrPatNoteLogDAO.getInstance().insertData(vo);
                            
                            OpNoteEmrPdfSheet opNoteEmrPdfSheet = new OpNoteEmrPdfSheet();
                            opNoteEmrPdfSheet.documentId = vo.getDocumentId();
                            opNoteEmrPdfSheet.setEmrPatNoteVO(vo);
                            opNoteEmrPdfSheet.setChapterDataMap(hm);
                            opNoteEmrPdfSheet.setChapterList(emrNoteChapterData);
                            opNoteEmrPdfSheet.setReport(orReport);
                            opNoteEmrPdfSheet.setActionType("C");                
            
                            if(tempEncounterNo.startsWith("I"))
                            {
                                this.loadEmrIpdEncounterData(tempEncounterNo);
                                opNoteEmrPdfSheet.setEmrIpdEncounterVO(GlobalParameters.getInstance().getEmrIpdEncounterVO());
                            }
                            else
                            {
                                Connection conn;
                                Statement stmt = null;
                                ResultSet rs = null;                                
                                
                                try
                                {               
                                    conn = DBConnectionHelper.getInstance().getMySqlConnection();
                                    stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                                    
                                    sql = new StringBuffer();
                                    sql.append(" SELECT * FROM emr.oer_patbas a WHERE a.encounter_no = '"+ tempEncounterNo +"' ");            
                                        
                                    rs = stmt.executeQuery(sql.toString());            
                                    if(rs.next())
                                    {                   
                                        EmrIpdEncounterVO tempEmrIpdEncounterVO = new EmrIpdEncounterVO();
                                        tempEmrIpdEncounterVO.setEncounterNo(rs.getString("encounter_no"));
                                        tempEmrIpdEncounterVO.setAdmitDate(rs.getDate("opd_date"));
                                        tempEmrIpdEncounterVO.setChartNo(rs.getString("chart_no"));
                                        tempEmrIpdEncounterVO.setIdNo(rs.getString("id_no"));
                                        tempEmrIpdEncounterVO.setPatName(rs.getString("pat_name"));
                                        tempEmrIpdEncounterVO.setBedNo(rs.getString("bed_no"));
                                        tempEmrIpdEncounterVO.setDeptCode(rs.getString("dept_code"));
                                        tempEmrIpdEncounterVO.setDeptName(rs.getString("dept_name"));
                                        tempEmrIpdEncounterVO.setVsCode(rs.getString("doc_code"));
                                        tempEmrIpdEncounterVO.setVsName(rs.getString("doc_name"));
                                        opNoteEmrPdfSheet.setEmrIpdEncounterVO(tempEmrIpdEncounterVO);
                                    }                                    
                                }
                                catch(Exception e)
                                {
                                    JOptionPane.showMessageDialog(this, "getOerPatbas Exception: " + e.toString());
                                }
                                finally
                                {
                                    DBConnectionHelper.getInstance().cleanRsAndStmt(rs,stmt);    
                                }                                                            
                            }
                            
                            opNoteEmrPdfSheet.doProcess();
                        }
                    }                     
                }
            }
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }
    
    /**
     * POPUP MESSAGE
     * */
    public void showMessage(String showMsg)
    {        
        JOptionPane.showMessageDialog(this, showMsg);
    }
           
    
    
    /**
     * ���յ{��
     * @param params
     */
    public static void main(String[] params)
    {
        UIUtils.setNoteLookAndFeel();
        //------���խӮ�-----                                                
        GlobalParameters.getInstance().setEncounterNo("I20090709");//I20030002
        GlobalParameters.getInstance().setChartNo("0614161");//0000000
        GlobalParameters.getInstance().setLoginUserName("012004");//��vI20090709
        
        /*
        GlobalParameters.getInstance().setEncounterNo("O18041201494");
        GlobalParameters.getInstance().setChartNo("0705327");
        GlobalParameters.getInstance().setLoginUserName("4066");//��v        
        GlobalParameters.getInstance().setDocumentNo("OOR-180400194");        
        */
        
        GlobalParameters.getInstance().setSys("");//OR
        GlobalParameters.getInstance().setDocType("VS");//APN�FVS
        GlobalParameters.getInstance().setUseVSTemplate("Y");        
        GlobalParameters.getInstance().setEnvArg("production"); //production / test
            
        System.out.println(GlobalParameters.getInstance().getEnvArg());
        
        JFrame jf = new JFrame();
        jf.setSize(1280, 900);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.getContentPane().setLayout(new BorderLayout());    
        NoteMainPanel panel = new NoteMainPanel(jf);        
        jf.getContentPane().add(panel, BorderLayout.CENTER);        
        //panel.setFormTitleHM.put("t1", "�f�����g�t��");
        panel.refreshFormTitle();
        
        jf.setVisible(true);
    }
}