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
 * addBtn.addActionListener 新增病歷
 * changeTextPanel:切換TAB執行之動作
 * checkOutDocument:佔用currentVO的編輯權限
 * checkInDocument:釋放currentVO的編輯權限
 * doEmrSheet:送內部簽章平台
 * directToDiagPage
 * delBtn.addActionListener 刪除病歷
 * getIPAddress:取得IP位址
 * getDutyDocInfo:取得ICU當班醫師
 * getCommNeedFlagByDate:稽核當日PROGRESS NOTE是否需要被COMMAND
 * getRptdbFlag:取得是否送簽院內簽章FLAG
 * limitsOfSave_2:完成之稽核
 * limitsOfSave_1:暫存之稽核
 * limitsOfSave:存檔時稽核該病歷是否可以被完成
 * loadPatNoteData:讀取EMR_PAT_NOTE資料
 * loadEmrPatList
 * rollBack:復原
 * saveData(String saveStatus): 1.暫存  2.完成
 * tempSave:暫存
 * saveEmrInterfaceTable:出摘外部送簽
 * doDiffPdf:版本比對
 * directToDiagPage:診斷維護
 * */

@SuppressWarnings("serial")
public class NoteMainPanel extends JPanel
{
    public String loadEmrPatListF = "loadEmrPatList";
    
    public EmrIpdEncounterVO emrIpdEncounterVO; //for 批次
	
    private Logger logger = Logger.getLogger(getClass());
    
    private JFrame sourceFrame;
  
    private BorderLayout borderLayout = new BorderLayout();
    private JPanel jPanel1 = new JPanel();
    private JPanel jPanel2 = new JPanel();
    private JPanel jPanel3 = new JPanel();
    private JPanel jPanel4 = new JPanel(); //病患查詢
    private JPanel jPanel5 = new JPanel(); //空白Panel
    private JPanel mPanel = new JPanel();
    private JLabel eventTimeLabel = new JLabel("病歷日期:");
    private JTextField eventTimeTextField = new JTextField();
    private DateChooserButton dcb1 = new DateChooserButton(eventTimeTextField);
    
    private FormLayout formLayout1 = new FormLayout("f:3dlu:n, f:315px:n, f:3dlu:n, f:794px:n, f:p:n", "c:3dlu:n, c:m:g, c:3dlu:n");    
    
    private JSplitPane jSplitPane1 = new JSplitPane();  //章節索引
    private JSplitPane jSplitPane2 = new JSplitPane();  //病歷清單
    private JSplitPane jSplitPane3 = new JSplitPane();  //病患清單
    
    private JPanel outsideNoteEkitPanel = new JPanel();
    
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JScrollPane jScrollPane2 = new JScrollPane();   //病患清單
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
    //update by leon 2012-06-05:增加VS範本頁籤，當登入者與患者之VS為不同人時顯示
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
    private JButton tmpBtn = new JButton("暫存",new ImageIcon(this.getClass().getResource("/images/savas_24.png")));    
    private JButton savBtn = new JButton("完成",new ImageIcon(this.getClass().getResource("/images/save_24.png"))); 
    
    //private JButton tmpBtn = new JButton("暫存");
    //private JButton senBtn = new JButton("送交");
    //private JButton retBtn = new JButton("退回");
    //private JButton savBtn = new JButton("完成");    
    
    
    private JButton rbkBtn = new JButton(new ImageIcon(this.getClass().getResource("/images/ref_24.png")));    
    
    private JButton logButton = new JButton("LogIt");
    
    private JButton queryButton = new JButton("查詢");        
    private JButton queryButton1 = new JButton("查詢大於5天");
    private JButton queryButton2 = new JButton("查詢大於3天");    
    private JButton queryButton3 = new JButton("查詢3天以內");
    
    
    private JButton dittoButton = new JButton("DITTO",new ImageIcon(this.getClass().getResource("/images/ditto.png")));    
    private JButton phraseButton = new JButton("插入片語",new ImageIcon(this.getClass().getResource("/images/phrase.png")));
    private JButton previewPrintButton = new JButton("預覽列印",new ImageIcon(this.getClass().getResource("/images/preview.png")));
    private JButton defaultValueButton = new JButton("預設範本",new ImageIcon(this.getClass().getResource("/images/tmpl.png")));    
    private JButton fourceEditButton = new JButton("強制編輯",new ImageIcon(this.getClass().getResource("/images/force.png")));
    private JButton directDiagButton = new JButton("診斷維護",new ImageIcon(this.getClass().getResource("/images/diag.png")));
    private JButton checkChartButton = new JButton("缺漏病歷",new ImageIcon(this.getClass().getResource("/images/loss.png")));
    
    private JButton hcaEmrButton = new JButton("補送簽章",new ImageIcon(this.getClass().getResource("/images/sign.png")));
    private JButton printButton = new JButton("紙本列印",new ImageIcon(this.getClass().getResource("/images/paper.png")));    
    private JButton diffPdfButton = new JButton("版本比對",new ImageIcon(this.getClass().getResource("/images/ver.png")));
    private JButton progressAllButton = new JButton("歷程總簽",new ImageIcon(this.getClass().getResource("/images/sign.png")));
    private JButton hcaEmrOpButton = new JButton("補送簽章(手術)",new ImageIcon(this.getClass().getResource("/images/sign.png")));
    private JButton pedigreeButton = new JButton("家族樹",new ImageIcon(this.getClass().getResource("/images/tree.png")));    
        
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
    
    private JLabel lblBranchCode = new JLabel("護理站:");
    private JLabel lblTreNoteName = new JLabel("處置名稱:");
    
    private JLabel lbldtlKind = new JLabel("子項分類:");
    
    private JLabel lblPersonInCharge = new JLabel("應簽章醫師:");
    private JTextField txtPersonInChargeName = new JTextField("");
    private JTextField txtPersonInChargeCode = new JTextField("");
    private JButton btnPersonInCharge = new JButton("...");
    private JButton btnQueryDept = new JButton("...");
    private JButton btnQueryVs = new JButton("...");
    private JButton btnQueryStation = new JButton("...");
    
    private JComboBox treNoteComboBox = new JComboBox();
    
    //update by jeff:歷程記載細分子類別
    private String[] wkSummaryComboBoxShowData = {"Progress Note", "Weekly Summary",
    		"Transfer Note", "On Service Note", "Duty Note"};    
    private JComboBox wkSummaryComboBox = new JComboBox(wkSummaryComboBoxShowData);
    
    //update by leon 2014-02-18:出院摘要細分子類別
    //private String[] dischgNoteComboBoxShowData = {"Dischg Summary", "Transfer Summary","Cut Summary"};
    private String[] dischgNoteComboBoxShowData = {"D:出院摘要", "T:轉科摘要","C:分段結清摘要"};   
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
    private JLabel lblDischgDays = new JLabel("天");
    
    private JRadioButton beHopRdo1 = null;
    private JRadioButton beHopRdo2 = null;
    private boolean beInHospitalFlag = true;
    
    private JComboBox beCompleteComboBox = new JComboBox(new String[]{"", "完成", "未完成"});
    private JComboBox beSignComboBox = new JComboBox(new String[]{"", "簽結", "未簽結"});
    
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
        "病歷號",
    	"病患姓名",
    	"病床號",     	
    	"主治醫師",  
    	"入院日期",
    	"出院日期",     		
        "入摘",
        "歷程",
        "出摘",
        "完成否",
        "簽結否", 
    	"就醫序號"};
    
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
            //Win7 1.5版Java會有TimeZone問題
            TimeZone.setDefault(TimeZone.getTimeZone("Asia/Taipei"));
            
            this.sourceFrame = jFrame;
            
            //初始化函數            
            jbInit();
                                            
            //this.reDoOpNoteEmrSheet();
                            
            //Win7 1.5版Java會有TimeZone問題
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
        
        //病歷撰寫畫面註冊,讓其可以對mainPanel做動作
        noteEkitPanel.setNoteMainPanel(this);
        
        //設定css
        noteEkitPanel.getEkitPanel().getEkitCore().setDefaultStyleSheet();
        htmlViewer.getEkitPanel().getEkitCore().setDefaultStyleSheet();
    
        loadEmrIpdEncounterData(GlobalParameters.getInstance().getEncounterNo());        
        
        loadSecUserData();
        
        //NoteMainPanel(整個頁面編排設定)
        {
            //設定NoteMainPanel的版面配置:使用BorderLayout將畫面切成NORTH、SOUTH、WEST、EAST、CENTER五個區塊
        	this.setLayout(borderLayout);
        	
            //在NORTH區塊加入『按鈕列』
            {
                this.add(getToolBar(), BorderLayout.NORTH);
            }
            
            //在CENTER區塊加入jPanel1
            {
                jPanel1.setLayout(formLayout1);
            	
                //左(2,2):TABS(jideTabbedPane)
                {
                    //設定jideTabbedPane的大小(Default)
                    jideTabbedPane.setPreferredSize(new Dimension(210, 290));
                    
                    //TAB3(jSplitPane3):病患清單
                    {
                        jSplitPane3.setOrientation(JSplitPane.VERTICAL_SPLIT);
                        jSplitPane3.setDividerLocation(160);
                                                
                        String columnSpecs = "70,0,80,0,30,0,115";
                        String rowSpecs = "25,3,25,3,25,3,25,3,25,3,25,3,25,3,25,3,25,3,25,3,25,3,25,3,25,3,25,3,25,3,25,3,25";
                        
                        jPanel4.setLayout(new FormLayout(columnSpecs, rowSpecs));
                        jPanel4.setBorder(BorderFactory.createTitledBorder("查詢"));
                        
                        jPanel4.add(new JLabel("住院序號:"),	new CellConstraints(1, 1, 1, 1, CellConstraints.RIGHT, CellConstraints.DEFAULT));
                        jPanel4.add(encounterNoField, 		new CellConstraints(3, 1, 1, 1, CellConstraints.FILL, CellConstraints.DEFAULT));
                        
                        jPanel4.add(new JLabel("病歷號碼:"),	new CellConstraints(1, 3, 1, 1, CellConstraints.RIGHT, CellConstraints.DEFAULT));
                        jPanel4.add(chartNoField, 			new CellConstraints(3, 3, 1, 1, CellConstraints.FILL, CellConstraints.DEFAULT));
                        
                        jPanel4.add(new JLabel("姓名:"),		new CellConstraints(1, 5, 1, 1, CellConstraints.RIGHT, CellConstraints.DEFAULT));
                        jPanel4.add(patNameField, 			new CellConstraints(3, 5, 1, 1, CellConstraints.FILL, CellConstraints.DEFAULT));
                        
                        jPanel4.add(new JLabel("床位:"),		new CellConstraints(1, 7, 1, 1, CellConstraints.RIGHT, CellConstraints.DEFAULT));
                        jPanel4.add(bedNoField, 			new CellConstraints(3, 7, 1, 1, CellConstraints.FILL, CellConstraints.DEFAULT));
                                                                                                
                        btnQueryDept.setVisible(false);
                        jPanel4.add(new JLabel("科別:"),		new CellConstraints(1, 9, 1, 1, CellConstraints.RIGHT, CellConstraints.DEFAULT));
                        jPanel4.add(deptCodeField, 			new CellConstraints(3, 9, 1, 1, CellConstraints.FILL, CellConstraints.DEFAULT));
                        jPanel4.add(btnQueryDept,			new CellConstraints(5, 9, 1, 1, CellConstraints.FILL, CellConstraints.CENTER));
                        jPanel4.add(lblDeptName, 			new CellConstraints(7, 9, 1, 1, CellConstraints.FILL, CellConstraints.DEFAULT));                        
                        
                        btnQueryVs.setVisible(false);
                        jPanel4.add(new JLabel("主治醫師:"),	new CellConstraints(1, 11, 1, 1, CellConstraints.RIGHT, CellConstraints.DEFAULT));
                        jPanel4.add(vsCodeField, 			new CellConstraints(3, 11, 1, 1, CellConstraints.FILL, CellConstraints.DEFAULT));
                        jPanel4.add(btnQueryVs,			    new CellConstraints(5, 11, 1, 1, CellConstraints.FILL, CellConstraints.CENTER));
                        jPanel4.add(lblVsName, 			    new CellConstraints(7, 11, 1, 1, CellConstraints.FILL, CellConstraints.DEFAULT));
                        
                        btnQueryStation.setVisible(false);
                        jPanel4.add(new JLabel("護理站:"), 	new CellConstraints(1, 13, 1, 1, CellConstraints.RIGHT, CellConstraints.DEFAULT));
                        jPanel4.add(branchCodeField, 		new CellConstraints(3, 13, 1, 1, CellConstraints.FILL, CellConstraints.DEFAULT));
                        jPanel4.add(btnQueryStation,		new CellConstraints(5, 13, 1, 1, CellConstraints.FILL, CellConstraints.CENTER));
                        jPanel4.add(lblStationName, 		new CellConstraints(7, 13, 1, 1, CellConstraints.FILL, CellConstraints.DEFAULT));
                        
                        jPanel4.add(new JLabel("住院日期:"),	new CellConstraints(1, 15, 1, 1, CellConstraints.RIGHT, CellConstraints.DEFAULT));
                        jPanel4.add(admitDateField,			new CellConstraints(3, 15, 1, 1, CellConstraints.FILL, CellConstraints.DEFAULT));
                        jPanel4.add(dcb_admitDate,			new CellConstraints(5, 15, 1, 1, CellConstraints.FILL, CellConstraints.CENTER));
                        
                        jPanel4.add(new JLabel("住院否:"), 	new CellConstraints(1, 17, 1, 1, CellConstraints.RIGHT, CellConstraints.DEFAULT));
                        beHopRdo1 = new JRadioButton("住院中");
                        beHopRdo2 = new JRadioButton("已出院", true);
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
                        
                        jPanel4.add(new JLabel("完成否:"), 	new CellConstraints(1, 21, 1, 1, CellConstraints.RIGHT, CellConstraints.DEFAULT));
                        jPanel4.add(beCompleteComboBox,	    new CellConstraints(3, 21, 1, 1, CellConstraints.FILL, CellConstraints.DEFAULT));
                        beCompleteComboBox.setSelectedIndex(0);
                                                
                        jPanel4.add(new JLabel("簽結否:"),         new CellConstraints(1, 23, 1, 1, CellConstraints.RIGHT, CellConstraints.DEFAULT));                        
                        jPanel4.add(beSignComboBox,     new CellConstraints(3, 23, 1, 1, CellConstraints.FILL, CellConstraints.DEFAULT));
                        beSignComboBox.setSelectedIndex(2);
                                                
                        jPanel4.add(queryButton,  new CellConstraints(7 ,23, 1, 1, CellConstraints.FILL, CellConstraints.DEFAULT));                                                                                                            
                                                
                        //update by leon 2015-07-24(五)
                        JLabel aLabel = new JLabel("轉醫師未完成");                                                
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
                        
                        jideTabbedPane.addTab("病患清單", jPanel4);
                        
                        if("OR".equals(GlobalParameters.getInstance().getSys()))
                        {
                            jideTabbedPane.setEnabledAt(0, false);
                        }
                    }
                    
                    //TAB1(jScrollPane1):病歷文件
                    {
                        //update by leon 2012-06-07:增加病患資料區塊
                        jSplitPane2.setOrientation(JSplitPane.VERTICAL_SPLIT);
                        jSplitPane2.setDividerLocation(350);                        
                        
                        jScrollPane1.getViewport().add(emrPatNoteTable, null);
                        
                        
                        
                        jSplitPane2.add(jScrollPane1, JSplitPane.BOTTOM);                        
                        
                        //病患資料
                        //設定元件版面配置
                        jPanel3.setLayout(new FormLayout("65,10,150","25,3,25,3,25,3,25,3,25,3,25,3,25,3,25,3,25,3,25,3,25"));
                        jPanel3.setBorder(BorderFactory.createTitledBorder("病患資料"));                              
                        jPanel3.add(new JLabel("就醫序號:"), new CellConstraints(1, 1, 1, 1, CellConstraints.RIGHT, CellConstraints.DEFAULT));
                        jPanel3.add(txtEncounterNo, new CellConstraints(3, 1, 1, 1, CellConstraints.FILL, CellConstraints.DEFAULT));                        
                        jPanel3.add(new JLabel("病歷號:"), new CellConstraints(1, 3, 1, 1, CellConstraints.RIGHT, CellConstraints.DEFAULT));
                        jPanel3.add(txtChartNo, new CellConstraints(3, 3, 1, 1, CellConstraints.FILL, CellConstraints.DEFAULT));                        
                        jPanel3.add(new JLabel("姓名:"), new CellConstraints(1, 5, 1, 1, CellConstraints.RIGHT, CellConstraints.DEFAULT));
                        jPanel3.add(txtName, new CellConstraints(3, 5, 1, 1, CellConstraints.FILL, CellConstraints.DEFAULT)); 
                        jPanel3.add(new JLabel("年齡:"), new CellConstraints(1, 7, 1, 1, CellConstraints.RIGHT, CellConstraints.DEFAULT));
                        jPanel3.add(txtAge, new CellConstraints(3, 7, 1, 1, CellConstraints.FILL, CellConstraints.DEFAULT)); 
                        jPanel3.add(new JLabel("生日:"), new CellConstraints(1 ,9, 1, 1, CellConstraints.RIGHT, CellConstraints.DEFAULT));
                        jPanel3.add(txtBirthDate, new CellConstraints(3, 9, 1, 1, CellConstraints.FILL, CellConstraints.DEFAULT));
                        jPanel3.add(new JLabel("床位:"), new CellConstraints(1, 11, 1, 1, CellConstraints.RIGHT, CellConstraints.DEFAULT));
                        jPanel3.add(txtBedNo, new CellConstraints(3, 11, 1, 1, CellConstraints.FILL, CellConstraints.DEFAULT)); 
                        jPanel3.add(new JLabel("科別:"), new CellConstraints(1, 13, 1, 1, CellConstraints.RIGHT, CellConstraints.DEFAULT));
                        jPanel3.add(txtDeptName, new CellConstraints(3, 13, 1, 1, CellConstraints.FILL, CellConstraints.DEFAULT));
                        jPanel3.add(new JLabel("主治醫師:"), new CellConstraints(1, 15, 1, 1, CellConstraints.RIGHT, CellConstraints.DEFAULT));
                        jPanel3.add(txtVsName, new CellConstraints(3, 15, 1, 1, CellConstraints.FILL, CellConstraints.DEFAULT));                                                                                              
                        jPanel3.add(new JLabel("就醫日期:"), new CellConstraints(1, 17, 1, 1, CellConstraints.RIGHT, CellConstraints.DEFAULT));
                        jPanel3.add(txtAdmitDate, new CellConstraints(3, 17, 1, 1, CellConstraints.FILL, CellConstraints.DEFAULT));                     
                        jPanel3.add(new JLabel("性別:"), new CellConstraints(1, 19, 1, 1, CellConstraints.RIGHT, CellConstraints.DEFAULT));
                        jPanel3.add(txtSex, new CellConstraints(3, 19, 1, 1, CellConstraints.FILL, CellConstraints.DEFAULT));
                        jPanel3.add(lblBranchCode, new CellConstraints(1, 21, 1, 1, CellConstraints.RIGHT, CellConstraints.DEFAULT));
                        jPanel3.add(txtBranchCode, new CellConstraints(3, 21, 1, 1, CellConstraints.FILL, CellConstraints.DEFAULT));
                        
                        //設定元件格式(顏色、粗體...等)
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
                        
                        jideTabbedPane.addTab("病歷文件", jSplitPane2);
                    }
                        
                    //TAB2(jSplitPane1):章節索引
                    {
                        jSplitPane1.setOrientation(JSplitPane.VERTICAL_SPLIT);
                        jSplitPane1.setDividerLocation(350);
                        
                        //上區(章節)(jScrollPane3)
                        {
                            jList1.setFont(new Font(jList1.getFont().getFontName(), Font.BOLD, 14));
                            jScrollPane3.getViewport().add(jList1);
                            jScrollPane3.setBorder(BorderFactory.createTitledBorder("章節"));      
                            jScrollPane3.setPreferredSize(new Dimension(250,400));      
                            jSplitPane1.add(jScrollPane3, JSplitPane.TOP);
                        }
                        
                        //下區(jideTabbedPane1)
                        {                        
                            jSplitPane1.add(jideTabbedPane1, JSplitPane.BOTTOM);
                        }
                        
                        jideTabbedPane.addTab("章節索引", jSplitPane1);
                    }
                    
                    jPanel1.add(jideTabbedPane,
                    		new CellConstraints(2, 2, 1, 1, CellConstraints.FILL, CellConstraints.FILL));
                }
                
                //右(4,2):jPanel2
                {   
                    //設定jPanel2的版面配置:使用CardLayout將畫面像撲克牌一樣一層層堆疊起來
                    jPanel2.setLayout(cardLayout); 
                    
                    //第一張卡(病例本文)(純顯示)
                    {
                        jPanel2.add("htmlViewer", htmlViewer);
                    }
                    
                    //第二張卡(維護病例本文)(可編輯)
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
                        //第一欄
                        eventTimePanel.add(eventTimeLabel,"0,0");
                        eventTimePanel.add(eventTimeTextField,"2,0");
                        eventTimePanel.add(dcb1,"3,0");
                        
                        //第二欄
                        eventTimePanel.add(lblTreNoteName,"5,0");
                        eventTimePanel.add(lbldtlKind,"5,0");                        
                                                
                        //第三欄
                        eventTimePanel.add(treNoteComboBox,"6,0");
                        eventTimePanel.add(wkSummaryComboBox,"6,0");  //歷程記載子分類
                        eventTimePanel.add(dischgNoteComboBox,"6,0"); //出院摘要子分類
                        
                        eventTimePanel.add(lblPersonInCharge,"8,0");
                        eventTimePanel.add(txtPersonInChargeName,"9,0"); //應簽章醫師姓名
                        eventTimePanel.add(btnPersonInCharge,"10,0"); //應簽章醫師選擇按鈕
                        eventTimePanel.add(txtPersonInChargeCode,"11,0"); //應簽章醫師代碼
                        
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
                    
                    //第三張卡(空白)--->(病患清單)
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
            //範本畫面注冊,可以讓撰寫畫面動作
            noteTemplatePanel.setNoteEkitPanel(noteEkitPanel);
            noteTemplateHosPanel.setNoteEkitPanel(noteEkitPanel);
            noteDataObjectPanel.setNoteEkitPanel(noteEkitPanel);
            
            jideTabbedPane1.addTab("個人範本", noteTemplatePanel); //tab2-1
            jideTabbedPane1.addTab("科範本", noteTemplateHosPanel); //tab2-2
            jideTabbedPane1.addTab("病患資料", noteDataObjectPanel); //tab2-3    
        }
        else
        {
            noteTemplatePanel = new NoteTemplatePanel(GlobalParameters.getInstance().getLoginUserName());
            noteTemplateVsPanel = new NoteTemplatePanel(GlobalParameters.getInstance().getEmrIpdEncounterVO().getVsCode());
            noteTemplateHosPanel = new NoteTemplatePanel(GlobalParameters.getInstance().getSecUserVO().getDeptCode());
            noteDataObjectPanel = new NoteDataObjectPanel();
            //範本畫面注冊,可以讓撰寫畫面動作
            noteTemplatePanel.setNoteEkitPanel(noteEkitPanel);
            noteTemplateVsPanel.setNoteEkitPanel(noteEkitPanel);
            noteTemplateHosPanel.setNoteEkitPanel(noteEkitPanel);
            noteDataObjectPanel.setNoteEkitPanel(noteEkitPanel);
        
            jideTabbedPane1.addTab("個人範本", noteTemplatePanel); //tab2-1
            jideTabbedPane1.addTab("VS範本", noteTemplateVsPanel); //tab2-2
            jideTabbedPane1.addTab("科範本", noteTemplateHosPanel); //tab2-3
            jideTabbedPane1.addTab("病患資料", noteDataObjectPanel); //tab2-4    
        }
        
        //設定下拉,滑鼠動作
        setAction();
        //工具列按鈕動作
        setToolBarButtonAction();
        //讀取自訂病患變數資料,個人範本資料,科範本資料
        loadCategoryData(); 
        //初始病歷撰寫的資料
        initNoteTextData();
        //設定病歷日期
        setEventTimeTextValue();
        //根據系統參數來設定UI狀態
        setUIStatus();
        //binding 驗證機制
        setBinding();
        
        //設定元件內容
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
            txtSex.setText(GlobalParameters.getInstance().getEmrIpdEncounterVO().getSexType().equals("F") ? "女" : "男");
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
            txtSex.setText(orVo.vtPatSex.equals("F") ? "女" : "男");
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
     * 修正排版按鈕動作
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
  
    /**建立工具列*/
    public JToolBar getToolBar()
    {
        JToolBar jtb = new JToolBar();
        jtb.setFloatable(false);            
        
        addBtn.setToolTipText("新增");
        delBtn.setToolTipText("刪除");
        tmpBtn.setToolTipText("暫存");
        savBtn.setToolTipText("完成");
        rbkBtn.setToolTipText("復原");
        
        
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
  
    /**建立工具列按鈕畫面*/
    public JPanel getToolBarPanel()
    {
        JPanel panel;
        
        //printButton.setEnabled(true);
        //directDiagButton.setEnabled(true);        
                
        //設定表格排版        
        //實際有放置元件的欄位中間間隔一個寬度為5的空欄位
        //第一列8個按鈕
        //第二列2個按鈕        
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
                
        panel.add(noteCategoryComboBox, "0,0");//病歷類別                
        panel.add(phraseButton, "2,0");        //插入片語
        panel.add(previewPrintButton, "4,0");  //預覽列印            
        panel.add(defaultValueButton, "6,0");  //預設範本
        panel.add(fourceEditButton, "8,0");    //強制編輯                                             
        panel.add(directDiagButton, "10,0");   //診斷維護        
                
        panel.add(dittoButton,"0,1");        //DITTO        
        panel.add(diffPdfButton, "2,1");     //版本比對
        panel.add(printButton, "4,1");       //紙本列印        
        panel.add(hcaEmrButton, "6,1");      //補送簽章        
        panel.add(progressAllButton, "8,1"); //歷程總簽
        panel.add(checkChartButton, "10,1"); //缺漏病歷
        panel.add(pedigreeButton, "12,1");  //家族樹        
        //panel.add(hcaEmrOpButton, "14,1"); //補送簽章(手)
        
        hcaEmrOpButton.setVisible(false);
        pedigreeButton.setVisible(true);
        pedigreeButton.setEnabled(true);
        
        if("012004".equals(GlobalParameters.getInstance().getLoginUserName()))
        {            
            hcaEmrButton.setEnabled(true);      //補送簽章
            printButton.setEnabled(true);       //紙本列印
            diffPdfButton.setEnabled(true);     //版本比對
            progressAllButton.setEnabled(true); //歷程總簽
            hcaEmrOpButton.setEnabled(true);    //補送簽章(手)
            pedigreeButton.setEnabled(true);    //家族樹
        }
        else if("95A029".equals(GlobalParameters.getInstance().getLoginUserName()))
        {            
            hcaEmrButton.setEnabled(true);       //補送簽章
            printButton.setEnabled(false);        //紙本列印
            diffPdfButton.setEnabled(true);     //版本比對
            progressAllButton.setEnabled(false); //歷程總簽
            hcaEmrOpButton.setEnabled(false);    //補送簽章(手)                    
        }        
        else
        {
            //重送電子簽章(隱藏)
            //hcaEmrButton = new JButton("");
            //hcaEmrButton.setBackground(new Color(0,0,0,0));            
            //hcaEmrButton.setBorder(BorderFactory.createLineBorder(new Color(0,0,0,0),15));
            //hcaEmrButton.setRolloverEnabled(false);
            //panel.add(hcaEmrButton, "16,0");
                                                                
            /*
            hcaEmrButton.setEnabled(true);       //補送簽章
            printButton.setEnabled(false);       //紙本列印
            diffPdfButton.setEnabled(true);     //版本比對
            progressAllButton.setEnabled(false); //歷程總簽
            hcaEmrOpButton.setEnabled(false);    //補送簽章(手)                        
            */
            
            hcaEmrButton.setEnabled(true);      //補送簽章
            printButton.setEnabled(true);       //紙本列印
            diffPdfButton.setEnabled(true);     //版本比對
            progressAllButton.setEnabled(true); //歷程總簽
            hcaEmrOpButton.setEnabled(true);    //補送簽章(手)
            pedigreeButton.setEnabled(true);    //家族樹
            
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
     * 根據系統參數來設定UI狀態
     */
    public void setUIStatus()
    {
        //若為手術系統登入，不允許新增或刪除，僅能修改手術內容
        if(GlobalParameters.getInstance().getSys() != null &&
        		GlobalParameters.getInstance().getSys().equals("OR"))
        {                    
            addBtn.setEnabled(false);
            delBtn.setEnabled(false);
            //治療處置選項
            lblTreNoteName.setVisible(false);
            treNoteComboBox.setVisible(false);
        }
    }
    
    /**
     * binding 驗證機制
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
     * 讀取病歷類別(下拉把[入摘、出摘....])資料(From Table emr_note_category)
     */
    public void loadCategoryData() throws Exception
    {
        try
        {
            //mark by leon 2012-05-18:依智翔指示修改DB 移除術前紀錄單的部分
            String sql = "select * from emr_note_category where use_kind = 'Swing' ";
            
            //若為手術系統登入，下拉選單預設只有手術可以選擇
            if(GlobalParameters.getInstance().getSys()!= null &&
            		GlobalParameters.getInstance().getSys().equals("OR"))
            {
                sql += "and note_type = '" + GlobalParameters.OR_NOTE + "' ";
            }
            sql += "order by id";
            
            //查詢並將結果置入下拉選單當作選項
            ArrayList emrNoteCategoryData = EmrNoteCategoryDAO.getInstance().findAllBySql(sql);
            HCComboBoxModel hcbm = new HCComboBoxModel(emrNoteCategoryData, "typeDesc",
            		"com.hcsaastech.ehis.doc.vo.EmrNoteCategoryVO");
            noteCategoryComboBox.setModel(hcbm);
            
            //預設選擇為ProgressNote
            EmrNoteCategoryVO defaultCategoryVO = null;
            for(int i=0; i<emrNoteCategoryData.size(); i++)
            {
                defaultCategoryVO = (EmrNoteCategoryVO) emrNoteCategoryData.get(i);
                //預設選取：歷程記載[ProgressNote]
                if("ProgressNote".equals(defaultCategoryVO.getNoteType()))                
                {
                    noteCategoryComboBox.setSelectedIndex(i);
                    break;
                }
            }
            
            //若查詢結果為空，預設選擇第一項
            if(defaultCategoryVO == null)
            {
                defaultCategoryVO = (EmrNoteCategoryVO) emrNoteCategoryData.get(0);
            }
            
            //若查詢結果不為空
            if(emrNoteCategoryData.size() > 0)
            {
                //依預設的類別去查詢所對應的章節清單
                loadChapterData(defaultCategoryVO.getId());
                //並將所選擇的類別儲存到全域變數filterString中
                this.filterString = defaultCategoryVO.getNoteType();
                //依所選擇的類別查詢病歷資料
                loadPatNoteData();
                //依病歷類別ID讀取自訂病患變數資料
                noteDataObjectPanel.loadDataobjectData(defaultCategoryVO.getId().toString(),((EmrNoteChapterVO)emrNoteChapterData.get(0)).getNoteChapter());
                noteDataObjectPanel.setNoteCategory(filterString);
                //依病歷類別ID讀取個人範本資料
                noteTemplatePanel.loadTemplatemstData(defaultCategoryVO.getNoteType());
                //update by leon 2012-06-04:依病歷類別ID讀取VS範本資料
                if(noteTemplateVsPanel != null){
                    noteTemplateVsPanel.loadTemplatemstData(defaultCategoryVO.getNoteType());
                }
                //依病歷類別ID讀取科範本資料
                noteTemplateHosPanel.loadTemplatemstData(defaultCategoryVO.getNoteType());                
            }
        }
        catch(Exception e)
        {
            MessageManager.showException(e.getMessage(),e);
            logger.debug(e.getMessage(),e);
        }
    }

    /**讀取章節資料*/
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
                                                    
            //總體完成否
            String strTotalFinish = " SELECT decode(COUNT(1),0,'Y','N') FROM emr.emr_pat_note_errlog WHERE emr_pat_note_errlog.encounter_no= en.encounter_no AND emr_pat_note_errlog.person_in_charge = '"+ GlobalParameters.getInstance().getLoginUserName() +"' ";
            //入摘完成否
            String strAdminNoteFinish = " SELECT decode(COUNT(1),0,'Y','N') FROM emr.emr_pat_note_errlog WHERE emr_pat_note_errlog.encounter_no= en.encounter_no AND emr_pat_note_errlog.report_subtype_scid = 'adminnote' AND emr_pat_note_errlog.person_in_charge = '"+ GlobalParameters.getInstance().getLoginUserName() +"' ";
            //歷程完成否
            String strProgressNoteFinish = " SELECT decode(COUNT(1),0,'Y','N') FROM emr.emr_pat_note_errlog WHERE emr_pat_note_errlog.encounter_no= en.encounter_no AND emr_pat_note_errlog.report_subtype_scid = 'progressnote' AND emr_pat_note_errlog.person_in_charge = '"+ GlobalParameters.getInstance().getLoginUserName() +"' ";
            //出摘完成否
            String strDischgNoteFinish = " SELECT decode(COUNT(1),0,'Y','N') FROM emr.emr_pat_note_errlog WHERE emr_pat_note_errlog.encounter_no= en.encounter_no AND emr_pat_note_errlog.report_subtype_scid = 'dischgnote' AND emr_pat_note_errlog.person_in_charge = '"+ GlobalParameters.getInstance().getLoginUserName() +"' ";                                    
            //簽結否
            String strSignStatus = " SELECT decode(COUNT(1),0,'N','Y') FROM emr.emr_diag_confirmlog WHERE emr_diag_confirmlog.encounter_no =en.encounter_no AND emr_diag_confirmlog.return_by IS NULL ";
            
            StringBuffer emrIpdEncounterSQL = new StringBuffer();                     
            emrIpdEncounterSQL.append(" SELECT * FROM (");                         
            //query_type:01:主治醫師為自己的清單
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
                    //控制Focus於Rows
                    emrPatListTable.setRowSelectionInterval(i, i);
                    //控制Focus於Columns
                    //emrPatListTable.setColumnSelectionInterval(0, 3);
                    emrPatListTable.setColumnSelectionInterval(0, emrPatTableColNames.length-1);
                    break;
                }
            }
            
            //20131129 Jeff 士弘指示:增加查詢結果欄位，加寬入院日期欄位無法秀出完整日期            
            emrPatListTable.setAutoResizeMode(0);
            emrPatListTable.setFont(new Font(emrPatListTable.getFont().getFontName(), Font.PLAIN, 16));
            emrPatListTable.setRowHeight(25);
                       
            emrPatListTable.getColumnModel().getColumn(0).setPreferredWidth(70);  //病歷號chartNo
            emrPatListTable.getColumnModel().getColumn(1).setPreferredWidth(120);  //病患姓名patName
            emrPatListTable.getColumnModel().getColumn(2).setPreferredWidth(80);  //床號bedNo            
            emrPatListTable.getColumnModel().getColumn(3).setPreferredWidth(90);  //主治醫師vsName            
            emrPatListTable.getColumnModel().getColumn(4).setPreferredWidth(90);  //入院日期admitDate            
            emrPatListTable.getColumnModel().getColumn(5).setPreferredWidth(90);  //出院日期closeDate            
            emrPatListTable.getColumnModel().getColumn(6).setPreferredWidth(50);  //入摘adminNoteFinish
            emrPatListTable.getColumnModel().getColumn(7).setPreferredWidth(50);  //歷程progressNoteFinish
            emrPatListTable.getColumnModel().getColumn(8).setPreferredWidth(50);  //出摘dischgNoteFinish   
            emrPatListTable.getColumnModel().getColumn(9).setPreferredWidth(50);  //完成否totalFinish
            emrPatListTable.getColumnModel().getColumn(10).setPreferredWidth(50); //簽結signStatus
            emrPatListTable.getColumnModel().getColumn(11).setPreferredWidth(110);//住院序號encounterNo
                                                              
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
                                              
            //Win7 1.5版Java會有TimeZone問題
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
                                                    
             //總體完成否
             String strTotalFinish = " SELECT decode(COUNT(1),0,'Y','N') FROM emr.emr_pat_note_errlog WHERE emr_pat_note_errlog.encounter_no= en.encounter_no AND emr_pat_note_errlog.person_in_charge = '"+ GlobalParameters.getInstance().getLoginUserName() +"' ";
             //入摘完成否
             String strAdminNoteFinish = " SELECT decode(COUNT(1),0,'Y','N') FROM emr.emr_pat_note_errlog WHERE emr_pat_note_errlog.encounter_no= en.encounter_no AND emr_pat_note_errlog.report_subtype_scid = 'adminnote' AND emr_pat_note_errlog.person_in_charge = '"+ GlobalParameters.getInstance().getLoginUserName() +"' ";
             //歷程完成否
             String strProgressNoteFinish = " SELECT decode(COUNT(1),0,'Y','N') FROM emr.emr_pat_note_errlog WHERE emr_pat_note_errlog.encounter_no= en.encounter_no AND emr_pat_note_errlog.report_subtype_scid = 'progressnote' AND emr_pat_note_errlog.person_in_charge = '"+ GlobalParameters.getInstance().getLoginUserName() +"' ";
             //出摘完成否
             String strDischgNoteFinish = " SELECT decode(COUNT(1),0,'Y','N') FROM emr.emr_pat_note_errlog WHERE emr_pat_note_errlog.encounter_no= en.encounter_no AND emr_pat_note_errlog.report_subtype_scid = 'dischgnote' AND emr_pat_note_errlog.person_in_charge = '"+ GlobalParameters.getInstance().getLoginUserName() +"' ";                                    
             //簽結否
             String strSignStatus = " SELECT decode(COUNT(1),0,'N','Y') FROM emr.emr_diag_confirmlog WHERE emr_diag_confirmlog.encounter_no =en.encounter_no AND emr_diag_confirmlog.return_by IS NULL ";
            
            StringBuffer emrIpdEncounterSQL = new StringBuffer();                     
            emrIpdEncounterSQL.append(" SELECT * FROM (");                         
            //query_type:01:主治醫師為自己的清單
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
                    //控制Focus於Rows
                    emrPatListTable.setRowSelectionInterval(i, i);
                    //控制Focus於Columns
                    //emrPatListTable.setColumnSelectionInterval(0, 3);
                    emrPatListTable.setColumnSelectionInterval(0, emrPatTableColNames.length-1);
                    break;
                }
            }
            
            //20131129 Jeff 士弘指示:增加查詢結果欄位，加寬入院日期欄位無法秀出完整日期            
            emrPatListTable.setAutoResizeMode(0);                        
            emrPatListTable.setFont(new Font(emrPatListTable.getFont().getFontName(), Font.PLAIN, 16));
            emrPatListTable.setRowHeight(25);
                       
            emrPatListTable.getColumnModel().getColumn(0).setPreferredWidth(70);  //病歷號chartNo
            emrPatListTable.getColumnModel().getColumn(1).setPreferredWidth(120);  //病患姓名patName
            emrPatListTable.getColumnModel().getColumn(2).setPreferredWidth(80);  //床號bedNo            
            emrPatListTable.getColumnModel().getColumn(3).setPreferredWidth(90);  //主治醫師vsName            
            emrPatListTable.getColumnModel().getColumn(4).setPreferredWidth(90);  //入院日期admitDate            
            emrPatListTable.getColumnModel().getColumn(5).setPreferredWidth(90);  //出院日期closeDate            
            emrPatListTable.getColumnModel().getColumn(6).setPreferredWidth(50);  //入摘adminNoteFinish
            emrPatListTable.getColumnModel().getColumn(7).setPreferredWidth(50);  //歷程progressNoteFinish
            emrPatListTable.getColumnModel().getColumn(8).setPreferredWidth(50);  //出摘dischgNoteFinish   
            emrPatListTable.getColumnModel().getColumn(9).setPreferredWidth(50);  //完成否totalFinish
            emrPatListTable.getColumnModel().getColumn(10).setPreferredWidth(50); //簽結signStatus
            emrPatListTable.getColumnModel().getColumn(11).setPreferredWidth(110);//住院序號encounterNo
                                                              
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
                                              
            //Win7 1.5版Java會有TimeZone問題
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
                                                    
             //總體完成否
             String strTotalFinish = " SELECT decode(COUNT(1),0,'Y','N') FROM emr.emr_pat_note_errlog WHERE emr_pat_note_errlog.encounter_no= en.encounter_no AND emr_pat_note_errlog.person_in_charge = '"+ GlobalParameters.getInstance().getLoginUserName() +"' ";
             //入摘完成否
             String strAdminNoteFinish = " SELECT decode(COUNT(1),0,'Y','N') FROM emr.emr_pat_note_errlog WHERE emr_pat_note_errlog.encounter_no= en.encounter_no AND emr_pat_note_errlog.report_subtype_scid = 'adminnote' AND emr_pat_note_errlog.person_in_charge = '"+ GlobalParameters.getInstance().getLoginUserName() +"' ";
             //歷程完成否
             String strProgressNoteFinish = " SELECT decode(COUNT(1),0,'Y','N') FROM emr.emr_pat_note_errlog WHERE emr_pat_note_errlog.encounter_no= en.encounter_no AND emr_pat_note_errlog.report_subtype_scid = 'progressnote' AND emr_pat_note_errlog.person_in_charge = '"+ GlobalParameters.getInstance().getLoginUserName() +"' ";
             //出摘完成否
             String strDischgNoteFinish = " SELECT decode(COUNT(1),0,'Y','N') FROM emr.emr_pat_note_errlog WHERE emr_pat_note_errlog.encounter_no= en.encounter_no AND emr_pat_note_errlog.report_subtype_scid = 'dischgnote' AND emr_pat_note_errlog.person_in_charge = '"+ GlobalParameters.getInstance().getLoginUserName() +"' ";                                    
             //簽結否
             String strSignStatus = " SELECT decode(COUNT(1),0,'N','Y') FROM emr.emr_diag_confirmlog WHERE emr_diag_confirmlog.encounter_no =en.encounter_no AND emr_diag_confirmlog.return_by IS NULL ";                                            
            
            StringBuffer emrIpdEncounterSQL = new StringBuffer();                     
            emrIpdEncounterSQL.append(" SELECT * FROM (");                         
            //query_type:01:主治醫師為自己的清單
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
                    //控制Focus於Rows
                    emrPatListTable.setRowSelectionInterval(i, i);
                    //控制Focus於Columns
                    //emrPatListTable.setColumnSelectionInterval(0, 3);
                    emrPatListTable.setColumnSelectionInterval(0, emrPatTableColNames.length-1);
                    break;
                }
            }
            
            //20131129 Jeff 士弘指示:增加查詢結果欄位，加寬入院日期欄位無法秀出完整日期            
            emrPatListTable.setAutoResizeMode(0);                        
            emrPatListTable.setFont(new Font(emrPatListTable.getFont().getFontName(), Font.PLAIN, 16));
            emrPatListTable.setRowHeight(25);
                       
            emrPatListTable.getColumnModel().getColumn(0).setPreferredWidth(70);  //病歷號chartNo
            emrPatListTable.getColumnModel().getColumn(1).setPreferredWidth(120);  //病患姓名patName
            emrPatListTable.getColumnModel().getColumn(2).setPreferredWidth(80);  //床號bedNo            
            emrPatListTable.getColumnModel().getColumn(3).setPreferredWidth(90);  //主治醫師vsName            
            emrPatListTable.getColumnModel().getColumn(4).setPreferredWidth(90);  //入院日期admitDate            
            emrPatListTable.getColumnModel().getColumn(5).setPreferredWidth(90);  //出院日期closeDate            
            emrPatListTable.getColumnModel().getColumn(6).setPreferredWidth(50);  //入摘adminNoteFinish
            emrPatListTable.getColumnModel().getColumn(7).setPreferredWidth(50);  //歷程progressNoteFinish
            emrPatListTable.getColumnModel().getColumn(8).setPreferredWidth(50);  //出摘dischgNoteFinish   
            emrPatListTable.getColumnModel().getColumn(9).setPreferredWidth(50);  //完成否totalFinish
            emrPatListTable.getColumnModel().getColumn(10).setPreferredWidth(50); //簽結signStatus
            emrPatListTable.getColumnModel().getColumn(11).setPreferredWidth(110);//住院序號encounterNo
                                                              
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
                                              
            //Win7 1.5版Java會有TimeZone問題
            //TimeZone.setDefault(TimeZone.getTimeZone("Asia/Taipei"));            
        }
        catch (Exception ex)
        {
            MessageManager.showException(ex.getMessage(), ex);
            logger.debug(ex.getMessage(), ex);
        }
    }
    
    /**
     * 讀取病患清單
     */
    public void loadEmrPatList()
    {
        try
        {
            loadEmrPatListF = "loadEmrPatList";
            //CHECK_01: 主治醫師不得為空
            //CHECK_02： 若住院否選擇已出院，天數最大不可超過500
        	
            Calendar _dischgDate = Calendar.getInstance(); //取得系統日用
        	        	    	
            //篩選出院日期起
            String _dischgStDate;             	
            _dischgDate.setTime(new Date());
            _dischgStDate = DateUtil.getDateString(_dischgDate.getTime(), "yyyy-MM-dd");
    		
            //篩選出院日期迄
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
        	
            String vsCodeValue = vsCodeField.getText(); //主治醫師
            String deptCodeValue = deptCodeField.getText(); //科別
            String branchCodeValue = branchCodeField.getText(); //護理站
            String encounterNoValue = encounterNoField.getText(); //住院序號
            String chartNoValue = chartNoField.getText(); //病歷號碼
            String patNameValue = patNameField.getText(); //姓名
            String bedNoValue = bedNoField.getText(); //床位
            String admitDateValue = admitDateField.getText(); //住院日期
          
            boolean vsCodeFlag = !StringUtil.isBlank(vsCodeValue);
            boolean deptCodeFlag = !StringUtil.isBlank(deptCodeValue);
            boolean branchCodeFlag = !StringUtil.isBlank(branchCodeValue);
            boolean encounterNoFlag = !StringUtil.isBlank(encounterNoValue);
            boolean chartNoFlag = !StringUtil.isBlank(chartNoValue);
            boolean patNameFlag = !StringUtil.isBlank(patNameValue);
            boolean bedNoFlag = !StringUtil.isBlank(bedNoValue);
            boolean admitDateFlag = !StringUtil.isBlank(admitDateValue);        	        	        	        	        	        	             	       
        	                                                                    
            /**
             * 1.查詢病患清單
             * 2.逐筆執行f_check_medical_chart
             * 3.原病患清單加上稽核結
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
                {//未輸入任依查詢條件，為避免查詢過大，給予預設查詢條件                    
                    if("28".equals(GlobalParameters.getInstance().getSecUserVO().getDeptCode()))
                    {//若登入者隸屬15F ICU，以護理站查詢                        
                        sql_1.append(" AND (bed_no LIKE'a15%' OR bed_no IN('A03112', 'A03113', 'A03212', 'A03213')) ");
                    }                
                    else if("2A".equals(GlobalParameters.getInstance().getSecUserVO().getDeptCode()))
                    {                        
                        sql_1.append(" AND (bed_no LIKE'A03%' AND bed_no NOT IN('A03112', 'A03113', 'A03212', 'A03213')) ");
                    }
                    else if("VS".equals(GlobalParameters.getInstance().getDocType()))
                    {//若登入者為VS                        
                        vsCodeValue = GlobalParameters.getInstance().getSecUserVO().getUsername();
                        System.out.println(vsCodeValue);
                        sql_1.append(" AND vs_code = '" + vsCodeValue + "' ");                        
                        vsCodeFlag = true;
                    }                
                    else
                    {//其它                        
                        sql_1.append(" AND dept_code = '" + GlobalParameters.getInstance().getSecUserVO().getDeptCode() + "' ");                    
                    }                                          
                }
                
                //住院否
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
                {//qStringBuffer sql_1 = new StringBuffer();uery_type:02:主治醫師不為但應簽章醫師為自己的清單
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
                                                    
            //總體完成否
            String strTotalFinish = " SELECT decode(COUNT(1),0,'Y','N') FROM emr.emr_pat_note_errlog WHERE emr_pat_note_errlog.encounter_no= en.encounter_no ";            
            //入摘完成否
            String strAdminNoteFinish = " SELECT decode(COUNT(1),0,'Y','N') FROM emr.emr_pat_note_errlog WHERE emr_pat_note_errlog.encounter_no= en.encounter_no AND emr_pat_note_errlog.report_subtype_scid = 'adminnote' ";            
            //歷程完成否                             
            String strProgressNoteFinish = " SELECT decode(COUNT(1),0,'Y','N') FROM emr.emr_pat_note_errlog WHERE emr_pat_note_errlog.encounter_no= en.encounter_no AND emr_pat_note_errlog.report_subtype_scid = 'progressnote' ";        
            //出摘完成否
            String strDischgNoteFinish = " SELECT decode(COUNT(1),0,'Y','N') FROM emr.emr_pat_note_errlog WHERE emr_pat_note_errlog.encounter_no= en.encounter_no AND emr_pat_note_errlog.report_subtype_scid = 'dischgnote' ";                                
            //簽結否
            String strSignStatus = " SELECT decode(COUNT(1),0,'N','Y') FROM emr.emr_diag_confirmlog WHERE emr_diag_confirmlog.encounter_no =en.encounter_no AND emr_diag_confirmlog.return_by IS NULL ";
            
            StringBuffer emrIpdEncounterSQL = new StringBuffer();                     
            emrIpdEncounterSQL.append(" SELECT * FROM (");                         
            //query_type:01:主治醫師為自己的清單
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
                //總體完成否
                String strTotalFinish_1 = " SELECT decode(COUNT(1),0,'Y','N') FROM emr.emr_pat_note_errlog WHERE emr_pat_note_errlog.encounter_no= en.encounter_no AND emr_pat_note_errlog.person_in_charge = '"+ vsCodeValue +"' ";
                //入摘完成否
                String strAdminNoteFinish_1 = " SELECT decode(COUNT(1),0,'Y','N') FROM emr.emr_pat_note_errlog WHERE emr_pat_note_errlog.encounter_no= en.encounter_no AND emr_pat_note_errlog.report_subtype_scid = 'adminnote' AND emr_pat_note_errlog.person_in_charge = '"+ vsCodeValue +"' ";
                //歷程完成否
                String strProgressNoteFinish_1 = " SELECT decode(COUNT(1),0,'Y','N') FROM emr.emr_pat_note_errlog WHERE emr_pat_note_errlog.encounter_no= en.encounter_no AND emr_pat_note_errlog.report_subtype_scid = 'progressnote' AND emr_pat_note_errlog.person_in_charge = '"+ vsCodeValue +"' ";
                //出摘完成否
                String strDischgNoteFinish_1 = " SELECT decode(COUNT(1),0,'Y','N') FROM emr.emr_pat_note_errlog WHERE emr_pat_note_errlog.encounter_no= en.encounter_no AND emr_pat_note_errlog.report_subtype_scid = 'dischgnote' AND emr_pat_note_errlog.person_in_charge = '"+ vsCodeValue +"' ";
                
                //query_type:02:主治醫師不為但應簽章醫師為自己的清單
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
            if("完成".equals(showStr))
            {
            	emrIpdEncounterSQL.append(" AND s.totalFinish = 'Y' ");            	                
            }
            else if("未完成".equals(showStr))
            {
            	emrIpdEncounterSQL.append(" AND s.totalFinish = 'N' ");                
            }
            
            String signStr = (String)beSignComboBox.getSelectedItem();
            if("簽結".equals(signStr))
            {
                emrIpdEncounterSQL.append(" AND s.signStatus = 'Y' ");                                 
            }
            else if("未簽結".equals(signStr))
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
                    //控制Focus於Rows
                    emrPatListTable.setRowSelectionInterval(i, i);
                    //控制Focus於Columns
                    //emrPatListTable.setColumnSelectionInterval(0, 3);
                    emrPatListTable.setColumnSelectionInterval(0, emrPatTableColNames.length-1);
                    break;
                }
            }
            
            //20131129 Jeff 士弘指示:增加查詢結果欄位，加寬入院日期欄位無法秀出完整日期            
            emrPatListTable.setAutoResizeMode(0);                        
            emrPatListTable.setFont(new Font(emrPatListTable.getFont().getFontName(), Font.PLAIN, 16));
            emrPatListTable.setRowHeight(25);
                                                       
            emrPatListTable.getColumnModel().getColumn(0).setPreferredWidth(70);  //病歷號chartNo
            emrPatListTable.getColumnModel().getColumn(1).setPreferredWidth(120);  //病患姓名patName
            emrPatListTable.getColumnModel().getColumn(2).setPreferredWidth(80);  //床號bedNo            
            emrPatListTable.getColumnModel().getColumn(3).setPreferredWidth(90);  //主治醫師vsName            
            emrPatListTable.getColumnModel().getColumn(4).setPreferredWidth(90);  //入院日期admitDate            
            emrPatListTable.getColumnModel().getColumn(5).setPreferredWidth(90);  //出院日期closeDate            
            emrPatListTable.getColumnModel().getColumn(6).setPreferredWidth(50);  //入摘adminNoteFinish
            emrPatListTable.getColumnModel().getColumn(7).setPreferredWidth(50);  //歷程progressNoteFinish
            emrPatListTable.getColumnModel().getColumn(8).setPreferredWidth(50);  //出摘dischgNoteFinish   
            emrPatListTable.getColumnModel().getColumn(9).setPreferredWidth(50);  //完成否totalFinish
            emrPatListTable.getColumnModel().getColumn(10).setPreferredWidth(50); //簽結signStatus
            emrPatListTable.getColumnModel().getColumn(11).setPreferredWidth(110);//住院序號encounterNo
                                                              
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
                                              
            //Win7 1.5版Java會有TimeZone問題
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
            //String[] headers = new String[] { "文件名稱" };
            
            String[] colNames;
            String[] headers;
            if("DischgNote".equals(filterString))
            {
            	/*
            	colNames = new String[] { "reportDateStr","enteredByName","reportStatusName","reportThirdTypeCode","lastUpdatedByName","personInChargeName" };
                headers = new String[] { "病歷日期","建立者","狀態","類","異動者","應簽章醫師"};
                */                
                colNames = new String[] { "reportDateStr","reportStatusName","reportThirdTypeCode","personInChargeName","lastUpdatedByName","createByName" };
                headers = new String[] { "病歷日期","狀態","類","應簽章醫師","異動者","建立者"};                
            }
            else if("ProgressNote".equals(filterString))
            {
            	/*
            	colNames = new String[] { "reportDateStr","enteredByName","reportStatusName","reportThirdTypeCode","lastUpdatedByName","commNeedFlagDesc","personInChargeName" };                
                headers = new String[] { "病歷日期","建立者","狀態","類","異動者","已評語","應簽章醫師" };
                */
                colNames = new String[] { "reportDateStr","reportStatusName","reportThirdTypeCode","commNeedFlagDesc","personInChargeName","lastUpdatedByName","createByName" };                
                headers = new String[] { "病歷日期","狀態","類","已評語","應簽章醫師","異動者","建立者" };
            }            	
            else
            {
            	colNames = new String[] { "reportDateStr","reportStatusName","personInChargeName","lastUpdatedByName","createByName"};
                headers = new String[] { "病歷日期","狀態","應簽章醫師","異動者","建立者"};
            }
                                               
            //取載入前的游標位置
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
            	
                emrPatNoteTable.getColumnModel().getColumn(0).setPreferredWidth(120); //報告日期
                emrPatNoteTable.getColumnModel().getColumn(1).setPreferredWidth(40);  //狀態
                emrPatNoteTable.getColumnModel().getColumn(2).setPreferredWidth(50);  //子類別
                emrPatNoteTable.getColumnModel().getColumn(3).setPreferredWidth(80);  //應簽章醫師
                emrPatNoteTable.getColumnModel().getColumn(4).setPreferredWidth(80);  //異動者
                emrPatNoteTable.getColumnModel().getColumn(5).setPreferredWidth(80);  //建立者
                
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
            	
            	emrPatNoteTable.getColumnModel().getColumn(0).setPreferredWidth(120); //報告日期
                emrPatNoteTable.getColumnModel().getColumn(1).setPreferredWidth(40);  //狀態
                emrPatNoteTable.getColumnModel().getColumn(2).setPreferredWidth(30);  //子類別
                emrPatNoteTable.getColumnModel().getColumn(3).setPreferredWidth(50);  //需評語否
                emrPatNoteTable.getColumnModel().getColumn(4).setPreferredWidth(80);  //應簽章醫師
                emrPatNoteTable.getColumnModel().getColumn(5).setPreferredWidth(80);  //異動者
                emrPatNoteTable.getColumnModel().getColumn(6).setPreferredWidth(80);  //建立者
                
                emrPatNoteTable.getColumnModel().getColumn(0).setCellRenderer(cdr);
                emrPatNoteTable.getColumnModel().getColumn(1).setCellRenderer(cdr);
                emrPatNoteTable.getColumnModel().getColumn(2).setCellRenderer(cdr);
                emrPatNoteTable.getColumnModel().getColumn(3).setCellRenderer(cdr);
                emrPatNoteTable.getColumnModel().getColumn(4).setCellRenderer(cdr);                
                emrPatNoteTable.getColumnModel().getColumn(5).setCellRenderer(cdr);
                emrPatNoteTable.getColumnModel().getColumn(6).setCellRenderer(cdr);  //需評語否
            }            	
            else
            {
            	emrPatNoteTable.setAutoResizeMode(0);
            	emrPatNoteTable.setRowHeight(25);
            	emrPatNoteTable.setFont(new Font(emrPatListTable.getFont().getFontName(), Font.PLAIN, 16));            	            
                
                emrPatNoteTable.getColumnModel().getColumn(0).setPreferredWidth(120); //報告日期
                emrPatNoteTable.getColumnModel().getColumn(1).setPreferredWidth(40);  //狀態
                emrPatNoteTable.getColumnModel().getColumn(2).setPreferredWidth(80);  //應簽章醫師                
                emrPatNoteTable.getColumnModel().getColumn(3).setPreferredWidth(80);  //異動者
                emrPatNoteTable.getColumnModel().getColumn(4).setPreferredWidth(80);  //建立者
                
                emrPatNoteTable.getColumnModel().getColumn(0).setCellRenderer(cdr);
                emrPatNoteTable.getColumnModel().getColumn(1).setCellRenderer(cdr);
                emrPatNoteTable.getColumnModel().getColumn(2).setCellRenderer(cdr);
                emrPatNoteTable.getColumnModel().getColumn(3).setCellRenderer(cdr);
                emrPatNoteTable.getColumnModel().getColumn(4).setCellRenderer(cdr);
            }
                                    
            if(list.size() > 0)
            {
                //將游標停留在原本的位置
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
                                                                              
                //顯示責任醫師
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
                
                //執行『歷程記載』檢查
                this.executeProgressNoteRule(currentVO);                               
            }
            else
            {
            	logger.info("[無病患基本資料] SQL:" + sql);
            }
        }
        catch (Exception ex)
        {
            MessageManager.showException(ex.getMessage(), ex);
            logger.debug(ex.getMessage(), ex);
        }
    }
    
    /**
     * 查詢User基本檔
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
     * 載入治療處置記錄單ExamOrderDesc
     */
    private void loadExamOrderDesc(){
        try
        {
            ArrayList ierOrderList = new ArrayList();
            ierOrderList.add(new HCComboBoxItem("治療處置", "治療處置"));  //Default
            
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
     * 設定頁面元件的動作<br>
     * 1.病歷類別下拉切換動作<br>
     * 2.病歷文件滑鼠點擊動作<br>
     * 3.病患清單滑鼠點擊動作<br>
     * 4.TabbedPane切換動作<br>
     * 5.章節清單滑鼠點擊事件<br>
     * 6.ekit加入key監聽器<br>
     * 7.Weekly summary JComboBox Action Event<br>
     */
    public void setAction()
    {
        //點選X關閉視窗：釋放currentVO的編輯權限
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
        
        //病歷類別下拉切換動作
        noteCategoryComboBox.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                boolean flag = checkData();
                if(flag)
                	return;
                
                if(noteCategoryComboBox.getSelectedItem() != null)
                {
                    //設定參數
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
                    
                    //處置名稱織輸入:選擇處置時才會顯示
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
                    
                    //取資料
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
                    
                    //初始畫面
                    rollBack();
                    initNoteTextData();
                }
            }
        });
        
        //病歷文件滑鼠點擊動作
        emrPatNoteTable.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e)
            {
                FunctionUtil functionUtil = new FunctionUtil();
                String editByCode = functionUtil.getEditByCode(currentVO.getId());
                
                //點擊二下後，切換而章節索引
                if(e.getClickCount() == 2)
                {
                    //未被CHECK OUT: 可編輯
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
                         JOptionPane.showMessageDialog(Utils.getParentFrame((JComponent)e.getSource()), "此份病歷資料目前由<"+ editByName +">編輯中","此份病歷資料目前由<"+ editByName +">編輯中", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                //點擊一下
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

        //病患清單滑鼠點擊動作
        emrPatListTable.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e)
            {
                //Double Click
                if(e.getClickCount() == 2)
                {
                    int _numOfRows = emrPatListTable.getSelectedRow();
                    int _numOfColumns = emrPatListTable.getSelectedColumn();
                	
                    //取得病患基本資料
                    loadEmrIpdEncounterData(((EmrIpdEncounterVO) emrPatListTable.getSelectedVO()).getEncounterNo());                      
                    //設定元件內容
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
                    txtSex.setText(GlobalParameters.getInstance().getEmrIpdEncounterVO().getSexType().equals("F") ? "女" : "男");
                    
                    //就醫序號
                    GlobalParameters.getInstance().setEncounterNo(GlobalParameters.getInstance().getEmrIpdEncounterVO().getEncounterNo());
                    //病歷號
                    GlobalParameters.getInstance().setChartNo(GlobalParameters.getInstance().getEmrIpdEncounterVO().getChartNo());    
                    
//                    JFrame jframe = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, noteMainPanel);                    
//                    String title = "";
//                    title += " 就醫序號:" + GlobalParameters.getInstance().getEmrIpdEncounterVO().getEncounterNo();
//                    title += " 病歷號:" + GlobalParameters.getInstance().getEmrIpdEncounterVO().getChartNo();
//                    title += " 床位:" + GlobalParameters.getInstance().getEmrIpdEncounterVO().getBedNo();
//                    jframe.setTitle(title);
                    
                    noteFormTitleHM.put("t2", "就醫序號:" + GlobalParameters.getInstance().getEmrIpdEncounterVO().getEncounterNo());
                    noteFormTitleHM.put("t3", "病歷號:" + GlobalParameters.getInstance().getEmrIpdEncounterVO().getChartNo());
                    noteFormTitleHM.put("t4", "床位:" + GlobalParameters.getInstance().getEmrIpdEncounterVO().getBedNo());
                    setFormTitleHM(noteFormTitleHM);
                    
                    //處置名稱織輸入:選擇處置時才會顯示
                    if("TreNote".equals(filterString))
                    {
                        loadExamOrderDesc();
                    }
                    
                    //20121120 Jeff 病患清單DOUBLE CLICK 入摘未完成，導頁時應該將類別自動跳成入摘 
                    if(_numOfRows > -1 && _numOfColumns > -1)
                    {
                	String _columnName = emrPatListTable.getColumnName(_numOfColumns);
                	//_columnName = ["入摘","歷程","出摘"] 才需要導 到特定頁面
                	if(!StringUtil.isBlank(_columnName) && (
                            _columnName.equals(emrPatTableHeaders[6]) || //入摘
                            _columnName.equals(emrPatTableHeaders[7]) || //歷程
                            _columnName.equals(emrPatTableHeaders[8])))	 //出摘                            
                        {
                            String _noteCategorySelItemValue = "歷程記載"; //default Value:歷程記載
                            if(_columnName.equals(emrPatTableHeaders[6]))
                            { //入摘
                                _noteCategorySelItemValue = "入院摘要";
                            }
                            else if(_columnName.equals(emrPatTableHeaders[8]))
                            { //出摘
                                _noteCategorySelItemValue = "出院摘要";
                            }
                            //病歷類別:noteCategoryComboBox須指定到對應字樣
                            //-->會觸發到noteCategoryComboBox的ActionListener
                            //System.out.println("" + noteCategoryComboBox.getSelectedItem());
                            noteCategoryComboBox.setSelectedItem(_noteCategorySelItemValue);
                            //System.out.println("" + noteCategoryComboBox.getSelectedItem());
                        }                        
                        else if(!StringUtil.isBlank(_columnName) && _columnName.equals(emrPatTableHeaders[10]))
                        {
                            String strTotalFinish = ((EmrIpdEncounterVO) emrPatListTable.getSelectedVO()).getTotalFinish();
                            String strSignStatus = ((EmrIpdEncounterVO) emrPatListTable.getSelectedVO()).getSignStatus();
                                                        
                            if("完成".equalsIgnoreCase(strTotalFinish) && "N".equalsIgnoreCase(strSignStatus))
                            {//已完成未簽結: 導頁診斷維護
                                directToDiagPage();
                            }
                            else
                            {//初始畫面                                
                                rollBack();
                            }
                        }                        
                        else
                        {//初始畫面                            
                            rollBack();
                        }
                    }
                    else
                    {//初始畫面                        
                	rollBack();
                    }
                }
            }
        });

        //TabbedPane切換動作
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
                                
                //章節索引(編輯)
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
                            JOptionPane.showMessageDialog(Utils.getParentFrame((JComponent)e.getSource()), "此份病歷資料目前由<"+ editByName +">編輯中","此份病歷資料目前由<"+ editByName +">編輯中", JOptionPane.INFORMATION_MESSAGE);
                            
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

        //章節清單滑鼠點擊事件
        jList1.addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent e)
            {
                if (e.getValueIsAdjusting())
                {
                    //點選章節時將章節名稱顯示在編輯區的TITLE
                    outsideNoteEkitPanel.setBorder(BorderFactory.createTitledBorder(jList1.getSelectedValue().toString()));
                    
                    syncTextPaneWithSourcePane();
                    
                    //update by leon 2012-07-10:章節切換執行修正
                    fixDocumentText();
                    hm.put(oldSeledtedItem, noteEkitPanel.getEkitPanel().getEkitCore().getDocumentBody());
                    
                    String _selectedValue = (String)jList1.getSelectedValue();
                                        
                    //update by leon 2014-08-25: FOR 出摘斷紙,出院診斷章節管控唯讀
                    setDischgDiagReadOnly();                    
                    
                    if(!StringUtil.isBlank(hm.get(_selectedValue)))
                    {                        
                    	
                    	noteEkitPanel.getEkitPanel().getEkitCore().getTextPane().setText(hm.get(_selectedValue).toString());
                    }
                    else
                    {
                        //update by leon 2012-05-09:修正若尚未輸入任何內容即使用病患資料功能或插入圖片無法正常顯示內容
                        noteEkitPanel.getEkitPanel().getEkitCore().getTextPane().setText("<p></p>");
                    }
                    
                    syncTextPaneWithSourcePane();
                    
                    oldSeledtedItem = jList1.getSelectedValue().toString();
                    oldSeledtedIndex = jList1.getSelectedIndex();
                    
                    //依病歷類別ID讀取自訂病患變數資料
                    EmrNoteCategoryVO vo = (EmrNoteCategoryVO) ((HCComboBoxModel) noteCategoryComboBox.getModel()).getVO();
                    noteDataObjectPanel.loadDataobjectData(vo.getId().toString(), oldSeledtedItem);
                    noteDataObjectPanel.setNoteCategory(filterString);
                }
            }
        });

        //在ekit加入key監聽器
        noteEkitPanel.getEkitPanel().getEkitCore().getTextPane().addKeyListener(new KeyAdapter(){            
            public void keyPressed(KeyEvent e)
            {
                //監聽ctrl + P的動作
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
                            JOptionPane.showMessageDialog(Utils.getParentFrame((JComponent)e.getSource()), "請先切換至病歷編輯狀態");
                        }
                    }
                }
                
                //update by leon 2012-05-28:因已有針對內容已MD5的方式進行修改判斷，故不在需要此段監聽邏輯
                //監聽修改動作
                /*
                if("R".equals(currentVO.getRowStatus()) && !ignoreKeyList.contains(e.getKeyCode()))
                {
                    System.out.println("EkitPane value change");
                    currentVO.setRowStatus("U");
                } 
                */
                
                //update by leon 2012-05-29:快捷鍵使用自訂貼上
                if(e.getKeyCode() == KeyEvent.VK_V)
                {
                    String _selectedValue = (String)jList1.getSelectedValue();
                    String _meanData = (String)dischgNoteComboBox.getSelectedItem();
                    _meanData = _meanData.split(":")[0];
                    
                    //System.out.println("_selectedValue: " + _selectedValue);
                    //System.out.println("_meanData: " + _meanData);
                    
                    if(!(("02.出院診斷".equals(_selectedValue)) && ("D".equals(_meanData) || "C".equals(_meanData))))
                    {
                        if(e.isControlDown())
                        {                    
                            noteEkitPanel.getEkitPanel().getEkitCore().jbtnPaste.doClick();
                            e.consume();
                        }
                    }
                }
                                                                        
                //update by leon 2012-05-29:快捷鍵使用上一步
                if(e.getKeyCode() == KeyEvent.VK_Z)
                {
                    if(e.isControlDown())
                    {
                        noteEkitPanel.getEkitPanel().getEkitCore().jbtnUndo.doClick();
                        e.consume();
                    }
                }
                
                //update by leon 2012-05-29:快捷鍵使用下一步
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
                    //取得下拉選單所選擇的值
                    String _meanData = (String)wkSummaryComboBox.getSelectedItem();
        		
                    //若有異動值
                    if(currentVO != null)
                    {                	                	
                	if(!_meanData.equalsIgnoreCase(currentVO.getReportThirdTypeScid()))
                	{                		
                            if(currentVO.getCreatedBy().equals("012004-1"))
                            {
                                showMessage("系統依規範自動建立之必要病歷，不得變更病歷子類別");                                
                                wkSummaryComboBox.setSelectedItem(currentVO.getReportThirdTypeScid());                                
                            }
                            else
                            {
                                currentVO.setReportThirdTypeScid(_meanData);
                                if(currentVO.getId() > 0)
                                {
                                    currentVO.setRowStatus("U");                                    
                                }
                                
                                //若選擇為Transfer Note或On Service Note彈出轉科清單供使用者選擇
                                if("Transfer Note".equals(_meanData) || "On Service Note".equals(_meanData))
                                {
                                    GetTranBedInfoDialog getTranBedInfoDialog = new GetTranBedInfoDialog("progressnote", _meanData, getInstance());
                                }
                            }
                	}                	                	
                    }        		        	
		}
        });
        
        //update by leon 2014-02-19:出摘拆分子類別,下拉選單異動更新VO對應欄位值
        dischgNoteComboBox.addActionListener(new ActionListener()
        {        	
            public void actionPerformed(ActionEvent e)
            {                
                String _meanData = (String)dischgNoteComboBox.getSelectedItem();
            	_meanData = _meanData.split(":")[0];
            	                            
		if(currentVO != null)
                {
                    //檢查reportThirdTypeScid欄位有無異動
                    if(!_meanData.equalsIgnoreCase(currentVO.getReportThirdTypeScid()))
                    {
                        if(currentVO.getCreatedBy().equals("012004-1"))
                        {
                            showMessage("系統依規範自動建立之必要病歷，不得變更病歷子類別");                                
                            
                            if("D".equals(currentVO.getReportThirdTypeScid()))
                            {
                                dischgNoteComboBox.setSelectedItem("D:出院摘要");
                            }
                            else if("T".equals(currentVO.getReportThirdTypeScid()))
                            {
                                dischgNoteComboBox.setSelectedItem("T:轉科摘要");
                            }
                            else if("C".equals(currentVO.getReportThirdTypeScid()))
                            {
                                dischgNoteComboBox.setSelectedItem("C:分段結清摘要");
                            }                                                    
                        }
                        else
                        {
                            //設定reportThirdTypeScid欄位
                            currentVO.setReportThirdTypeScid(_meanData);
                            if(currentVO.getId() > 0)
                            {//非新增->rowStatus = U
                                //update by leon 2014-05-26: 待修正變更大類別未選擇子類別也會觸發
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
     * 設定工具列按鈕監聽器
     * @see
     */
    public void setToolBarButtonAction() {
        //新增按鈕動作
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
                    
                    //建立新增資料
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
                    
                    //新增病歷時,若登入者為主治醫師,應簽章醫師即為登入者
                    if("VS".equals(GlobalParameters.getInstance().getDocType()))
                    {
                    	newVO.setAdviserByScid(GlobalParameters.getInstance().getLoginUserName());
                    	newVO.setAdviserByName(docName);
                        newVO.setPersonInCharge(GlobalParameters.getInstance().getLoginUserName());
                    }
                    //登入者非主治醫師時
                    else
                    {
                    	String strBranchCode = GlobalParameters.getInstance().getEmrIpdEncounterVO().getBranchCode();
                        //若為ICU應簽章醫師為當班醫師
                    	if("A031".equals(strBranchCode) || "A032".equals(strBranchCode) || "A152".equals(strBranchCode))
                    	{
                            //取得當班醫師，若未設定取原主治醫師
                            HashMap dutyDocInfo = getDutyDocInfo(GlobalParameters.getInstance().getEncounterNo());
                                        	
                            newVO.setAdviserByScid(dutyDocInfo.get("doc_code").toString());
                            newVO.setAdviserByName(dutyDocInfo.get("doc_name").toString());
                            newVO.setPersonInCharge(dutyDocInfo.get("doc_code").toString());
                    	}
                    	//非ICU應簽章醫師為原主治醫師
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
                        //Weekly summary 選項及預設值:Progress Note
                        String _newWkSummaryData = wkSummaryComboBoxShowData[0];
                        
                        //設定reportThirdTypeScid欄位
                        newVO.setReportThirdTypeScid(_newWkSummaryData);
                        //設定下拉選項
            		//wkSummaryComboBox.setSelectedItem(newVO.getReportThirdTypeScid());
                    }
                    
                    //update by leon 2014-02-19:出摘新增時,預設子類別為D:出院摘要
                    if("DischgNote".equals(filterString))
                    {
                        String _newDischgNoteData = dischgNoteComboBoxShowData[0]; //D:出院摘要
                        newVO.setReportThirdTypeScid(_newDischgNoteData.split(":")[0]); //D                      
                    }
                    
                    //預設為不需要VS COMMENT,若登入者非D1主治醫師設定為需要VS COMMENT
                    String commNeedFlag = "N";
                    if(!"D1".equals(GlobalParameters.getInstance().getSecUserVO().getUserGroup()))
                    {
                    	commNeedFlag = "Y";
            		}
                    newVO.setCommNeedFlag(commNeedFlag);
                    
                    //更新畫面
                    emrPatNoteTable.addVO(0, newVO);
                    //emrPatNoteTable.setRowSelectionInterval(emrPatNoteTable.getRowCount() - 1, emrPatNoteTable.getRowCount() - 1);
                    currentVO = newVO;
                    setEkitCoreDocumentId(currentVO.getDocumentId());
                    setEventTimeTextValue();
                    setExamOrderDescValue();

                    initNoteTextData();

                    //切換當章節索引
                    jideTabbedPane.setSelectedIndex(2);
                    
                } catch (Exception ex) {
                    MessageManager.showException(ex.getMessage(), ex);
                    logger.debug(ex.getMessage(), ex);
                } finally {
                    DBConnectionHelper.getInstance().cleanConnection(conn);
                }
            }
        });
    
        //刪除按鈕動作
        delBtn.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e)
        {        
        	try
        	{
        		if(currentVO != null)
        		{
        			if(currentVO.getReportStatusScid().equals("2"))
        			{
        				JOptionPane.showMessageDialog(null,"已完成資料無法刪除!");
        				return;
        			}
        			
        			if("012004-1".equals(currentVO.getCreatedBy()))
        			{
        				JOptionPane.showMessageDialog(null,"系統依規範自動建立之必要病歷，不得刪除");
        				return;
        			}
               
        			int op = JOptionPane.showConfirmDialog(null,"是否刪除該筆資料?","Info",JOptionPane.OK_CANCEL_OPTION);
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
        					//電子病歷        					
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
        				//JOptionPane.showMessageDialog(null, "刪除成功!");   
        			}
        		}
        	}
        	catch (Exception ex)
        	{
        		MessageManager.showException(ex.getMessage(),ex);
        		logger.debug(ex.getMessage(),ex);
        	}
        }});

        //暫存按鈕動作
        tmpBtn.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e)
        {            
            tempSave();
        }});

        //存檔按鈕動作
        savBtn.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e)
        {
            if(currentVO != null)
            {
                try
        	{                    
                    //存檔前稽核
                    if(limitsOfSave(true) == false){return;}

                    //稽核資料是否有異動
                    checkData2();                    	                    
                    
                    //修正HTML排版字元
                    fixDocumentText();
                    
                    //存檔修正rowStatus
                    if("R".equals(currentVO.getRowStatus())){currentVO.setRowStatus("U");}
                    
                    //存檔
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
    
        //復原,放棄存檔按鈕動作
        rbkBtn.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e)
        	{
        		setDischgDiagReadOnly();        		
        		rollBack();
        	}
        });
    
        //片語按鈕動作  
        phraseButton.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
            if(jideTabbedPane.getSelectedIndex() == 2){
              String phraseType = chapterHm.get(jList1.getSelectedValue().toString())!=null ? chapterHm.get(jList1.getSelectedValue().toString()).toString() : "";
              //System.out.println("phraseType: " + phraseType);
              new NotePhraseDialog(noteEkitPanel,phraseType);
            }else
              JOptionPane.showMessageDialog(Utils.getParentFrame((JComponent)e.getSource()), "請先切換至病歷編輯狀態");
          }
        });
              
        //量審稽核
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
        
        //責任醫師選擇
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
        
        //預覽按鈕動作  
        previewPrintButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if(!currentVO.getRowStatus().equals("R"))
                {
                    JOptionPane.showMessageDialog(null, "資料未存檔,無法預覽", "Info", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                
                if(currentVO.getReportStatusScid().equals("T"))
                {
                    JOptionPane.showMessageDialog(null, "資料未存檔, 無法預覽", "Info",
                    		JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
    
                try
                {
                    /*將病歷撰打字串,編輯成列印字串*/
                    ArrayList list = new ArrayList();
                    JTextPane textPane = new JTextPane();
                    textPane.setContentType("text/html");
                    
                    //依章節
                    Iterator it = chapterOrderArray.iterator();
                    while(it.hasNext())
                    {
                        String key = it.next().toString();
                        //取得該章節編輯的內容
                        String content = String.valueOf(hm.get(key));                                    
                        
                        //檢核該章節內容是否為空，TRUE：空，FALSE：非空
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
                            
                            //去除空白
                            temp = temp.replaceAll(" ","");
                            temp = temp.replaceAll("&#160;","");
                            temp = temp.replaceAll("&nbsp;","");
                            
                            //去除換行
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
                                                                            
                        //如果沒有內容,連章節也不印                                    
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
                     * 列印動作
                     * 手術報告無住院主檔，故不需EmrIpdEncounterVo
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
                        JOptionPane.showMessageDialog(null, "手術紀錄單尚未完成，請完成紀錄單再列印!");
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
    
        //列印按鈕動作
        printButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                //檢核主頁面上所選擇之以打病歷清單,狀態須為R(唯獨)才可執行列印功能
                if(!currentVO.getRowStatus().equals("R"))
                {
                    JOptionPane.showMessageDialog(null, "資料未存檔, 無法列印", "Info",
                    		JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                
                if(currentVO.getReportStatusScid().equals("T"))
                {
                    JOptionPane.showMessageDialog(null, "資料未存檔, 無法列印", "Info",
                    		JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                
                
                try
                {
                    String noteType = filterString; //病例維護類別(出摘、入摘、歷程...)
                    String branchCode = null; //指定列印樓層
                    String groupCode = null; //指定列印印表機
                    String copies = "1"; //列印份數
                    String parms = ""; //包含起始行數,列印類別,與DOCUMENT ID
                    
                    //若為歷程記載
                    if("ProgressNote".equals(filterString))
                    {
                        //跳出列印視窗
                        ProgressNotePrintDialog progressNotePrintDialog = new ProgressNotePrintDialog(
                        		GlobalParameters.getInstance().getEmrIpdEncounterVO().getBranchCode());
                        
                        //取得列印視窗中所設定的相關屬性
                        ArrayList rs = progressNotePrintDialog.selectedVOs;
                        branchCode = progressNotePrintDialog.branchCode;
                        groupCode = progressNotePrintDialog.groupCode;
                        copies = progressNotePrintDialog.copies;
                        
                        //若設定的起始行數不為0,noteType附加"_MPtray"
                        if(!("0".equals(progressNotePrintDialog.lineNo)))
                        {
                            noteType += "_MPtray";
                        }
                  
                        //設定parms
                        parms += "lineNo:" + progressNotePrintDialog.lineNo + ";";
                        //update by leon 2012-04-10:歷程記錄之列印,增加列印空白單的功能
                        parms += "printType:" + progressNotePrintDialog.printType + ";";
                
                        for(int i=0;i<rs.size();i++)
                        {
                            EmrPatNoteVO vo = (EmrPatNoteVO)rs.get(i);
                            parms += "DocumentId" + i + ":" + vo.getDocumentId() + ";";
                        }
                    }
                    //若為手術
                    else if("OpNote".equals(filterString))
                    {
                        //檢核手術記錄單完成否
                        OrReport report = new OrReport(currentVO.getSheetXmlMeta());
                        if ("N".equals(report.getOrVo().feeOpFlag)) {
                            JOptionPane.showMessageDialog(null, "手術紀錄單尚未完成，請完成紀錄單再列印!");
                            return;
                        }
                        
                        PrinterLocationDialog printerLocationDialog = new PrinterLocationDialog("OR");
                        branchCode = printerLocationDialog.branchCode;
                        groupCode = printerLocationDialog.groupCode;
                        copies = printerLocationDialog.copies;
                        
                    }
                    //其他
                    else
                    {
                        PrinterLocationDialog printerLocationDialog = new PrinterLocationDialog(
                        		GlobalParameters.getInstance().getEmrIpdEncounterVO().getBranchCode());
                        branchCode = printerLocationDialog.branchCode;
                        groupCode = printerLocationDialog.groupCode;
                        copies = printerLocationDialog.copies;
                    }
    
                    //call 列印webservice
                    if(branchCode!=null)
                    {
                        logger.debug("==列印參數==");
                        logger.debug("NoteType:" + noteType);
                        logger.debug("branchCode:" + branchCode);
                        logger.debug("groupCode:" + groupCode);
                        logger.debug("copies:" + copies);
                        logger.debug("DocumentId:" + currentVO.getDocumentId());
                        logger.debug("EncounterNo:" + GlobalParameters.getInstance().getEncounterNo());
                        logger.debug("Parameters:" + parms);
                        logger.debug("列印開始...");
                        
                        System.out.println("==列印參數==");
                        System.out.println("NoteType:" + noteType);
                        System.out.println("branchCode:" + branchCode);
                        System.out.println("groupCode:" + groupCode);
                        System.out.println("copies:" + copies);
                        System.out.println("DocumentId:" + currentVO.getDocumentId());
                        System.out.println("EncounterNo:" + GlobalParameters.getInstance().getEncounterNo());
                        System.out.println("Parameters:" + parms);
                        System.out.println("列印開始...");
                
                        //update by leon 2012-06-08:列印時使用THREAD機制                        
                        new PrintThread(noteType,branchCode,currentVO.getDocumentId(),GlobalParameters.getInstance().getEncounterNo(),parms,copies,GlobalParameters.getInstance().getChartNo(),groupCode,currentVO.getReportThirdTypeScid()).start();
                        //本機
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
    
        //ditto按鈕動作
        dittoButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                /**若選擇章節索引範本*/
                if(jideTabbedPane.getSelectedIndex() == 2)
                {
                    /**若選擇出摘或病摘*/
                    if(filterString.equals("DischgNote") || filterString.equals("ChtDischgNote") || filterString.equals("AdminNote"))
                    {
                        new DittoDialog(DittoDialog.TYPE1, filterString, emrNoteChapterData, getInstance());
                    }
                    /**若選擇歷程記載*/
                    else if(filterString.equals("ProgressNote"))
                    {
                        new DittoDialog(DittoDialog.TYPE2, filterString, emrNoteChapterData, getInstance());                        
                    }
                }
                /**若選擇以打病歷清單*/
                else
                {
                    JOptionPane.showMessageDialog(Utils.getParentFrame((JComponent)e.getSource()), "請先切換至病歷編輯狀態");
                }          
          }
        });
    
        //預設範本按鈕動作
        defaultValueButton.addActionListener(new ActionListener(){
          public void actionPerformed(ActionEvent e){
            try
            {
                if(jideTabbedPane.getSelectedIndex() != 2){
                    JOptionPane.showMessageDialog(Utils.getParentFrame((JComponent)e.getSource()), "請先切換至病歷編輯狀態");
                    return;
                }
            
                //update by leon 2012-03-27:取消新增出院摘要時的預設查詢,改為讓USER可選擇何時按下預設值按鈕來查詢
                //if(filterString.equals("DischgNote") ||filterString.equals("ChtDischgNote") )
                if(filterString.equals("DischgNote"))
                {
                    setDefaultDischgNoteData();    
                    noteEkitPanel.setDataHasChangedStatus(true);
                }
                else
                {
                    //取得預設值WS
                    DocDataObjectStub stub = new DocDataObjectStub();
                    stub.setEndpoint(GlobalParameters.getInstance().getDocServerURL());
                    notews.ResultDTO dto = stub.getNoteDefaultValue(
                    		GlobalParameters.getInstance().getEncounterNo(),
                    		GlobalParameters.getInstance().getChartNo(),
                    		filterString,
                    		"");
                    
                    if(dto.getResult()!=null && dto.getResult().length()>0){
                      //System.out.println(dto.getResult());
                      //將xml編譯
                      Document document = DocumentHelper.parseText(dto.getResult());            
                      Element rootElement = document.getRootElement();
                      //處理資料
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
                      //設定畫面
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
        
        //強制編輯
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
        
        //版本比對
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
         
        //歷程總簽
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
        
        //診斷維護
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
        
        //補送簽章(院)
        hcaEmrButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    if(getRptdbFlag())
                    {
                        /*
                        //批次補發
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
                                                                                                        
                        //單筆補發
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
        
        //補送簽章(手)
        hcaEmrOpButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                try
                {                                                    
                    //手術報告補發
                    batchReDoOpNoteEmrSheet();                                    
                }
                catch (Exception ex)
                {
                    MessageManager.showException(ex.getMessage(),ex);
                    logger.debug(ex.getMessage(),ex);
                }
            }
        });
        
        //家族樹
        pedigreeButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                try
                {                    
                    StringBuffer strUrl = new StringBuffer();
                    //strUrl.append("rundll32 url.dll,FileProtocolHandler ");
                    strUrl.append(GlobalParameters.getInstance().getFamilyTreeUrl());                    
                    strUrl.append(("?chHospArea=" + "XD")); //院區 花蓮HL、台中TC、大林DL、台北TP
                    strUrl.append(("&chOCaseNo=" + GlobalParameters.getInstance().getEncounterNo())); //住院號
                    strUrl.append(("&chMrNo=" + GlobalParameters.getInstance().getChartNo())); //病歷號
                    strUrl.append(("&chSaveDT=" + "1070820000000")); //紀錄時間(感覺沒啥作用 我都塞固定值)                            
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
              
        //主治醫師TextField
        vsCodeField.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    loadEmrPatList();
                }
            }
        });
        
        //----------查詢頁面功能鈕 Start----------
        //查詢
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
         * 科別LOV
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
         * 主治醫師LOV
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
         * 護理站LOV
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
        
        //科別TextField
        deptCodeField.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    loadEmrPatList();
                }
            }
        });
        
        //護理站TextField
        branchCodeField.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    loadEmrPatList();
                }
            }
        });
        
        //住院中
        beHopRdo1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				beInHospitalFlag = true;
			}
        });
        //已出院(預設七日)
        beHopRdo2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				beInHospitalFlag = false;
			}
        });
        
        //----------查詢頁面功能鈕 End----------
  }
  
    //update by leon 2012-06-08:列印時使用THREAD機制
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
                if (this.groupCode == null) {//本機
                    NoteReport.print(noteType,branchCode,currentVO.getDocumentId(),GlobalParameters.getInstance().getEncounterNo(),parms,copies,GlobalParameters.getInstance().getChartNo());
                    logger.debug("列印結束");
                } else {
                    RepBasStub stub = new RepBasStub();
                    stub.setEndpoint(GlobalParameters.getInstance().getReportServerURL());
                    ResultDTO dto = stub.printNoteReport(noteType,branchCode,currentVO.getDocumentId(),GlobalParameters.getInstance().getEncounterNo(),parms,copies,GlobalParameters.getInstance().getChartNo(),groupCode);
                    if(dto.getMessage().length() > 0)
                    {
                        JOptionPane.showMessageDialog(null,"列印回傳訊息:" + dto.getMessage());
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
     * 設定出院摘要預設值(取入院摘要章節內容塞入出院摘要章節)
     * update by leon 2012-03-27:撰寫出摘時預設帶入摘資料,因入摘資料可能撰寫多筆,舊時為全部都帶進來,新修改為只帶入最後一筆入摘資料
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
            sql += "    date_created DESC "; /**由新到舊排序*/
            
            //取得資料           
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
                    //分章節
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
                    break; /**只處理排序後的第一筆(只抓最新的資料)*/
                }
                //放入資料
                HashMap dischgNoteDefaultValue = new HashMap();
                dischgNoteDefaultValue.put("01.入院診斷", impression);
                //dischgNoteDefaultValue.put("02.出院診斷", impression);
                dischgNoteDefaultValue.put("03.主  述", chiefComplaint);
                //dischgNoteDefaultValue.put("04.病  史", presentIllness + "\n" + pastHistory + "\n" + personalHistory + "\n" + familyHistory);
                String _04 = "<p><b>Present Illness:</b></p>" + presentIllness +
	                		 "<p><b>Past History:</b></p>" + pastHistory +
	                		 "<p><b>Personal History:</b></p>" + personalHistory +
	                		 "<p><b>Family History:</b></p>" + familyHistory;
                dischgNoteDefaultValue.put("04.病  史", _04);
                dischgNoteDefaultValue.put("05.體檢發現", physicalExam);
                dischgNoteDefaultValue.put("09.檢查記錄", labFinding);
                //更新畫面
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
            //取得資料           
            ArrayList list = EmrSheetDefinitionDAO.getInstance().findAllBySql(sql);

            if (list.size() > 0) {
                EmrSheetDefinitionVO vo = (EmrSheetDefinitionVO) list.get(0);
                //設定資料
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
     * 設定Ekit Document id
     * @param documentId
     */
    public void setEkitCoreDocumentId(String documentId) {
        noteEkitPanel.getEkitPanel().getEkitCore().documentId = documentId;
    }

    /**
     * 檢查資料是否有修改
     * @see
     */
    public boolean checkData() {
    	boolean flag = false;
        try {
            if (currentVO != null) {
                //比對VO資料是否變更
                //Win7 1.5版Java會有TimeZone問題
            	//System.out.println("@getRowStatus: " + currentVO.getRowStatus());
            	
            	TimeZone.setDefault(TimeZone.getTimeZone("Asia/Taipei"));
                binding.setVo(currentVO);
                binding.compareData();

                //System.out.println("@getRowStatus: " + currentVO.getRowStatus());
                
                if (currentVO != null && !currentVO.getRowStatus().equals("R")) {
                    //int op = JOptionPane.showConfirmDialog(null, "資料已有修改，是否離開？", "Info", JOptionPane.OK_CANCEL_OPTION);
                	int op = JOptionPane.showOptionDialog(null, "資料已有修改，是否離開？", "Info",
                			JOptionPane.OK_CANCEL_OPTION, -1, null, new String[]{"是", "否"}, "是");
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
     * 比對VO資料(EmrPatNoteVO)是否變更
     */
    public void checkData2()
    {
        try
        {
            if(currentVO != null)
            {
                //比對VO資料是否變更
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
     * 存檔前稽核是否可以存檔
     * */
    public boolean limitsOfSave(boolean saveFlag)
    {
    	boolean rtnLimistFlag = true; //預設為可存檔    	
        
        //完成鈕的稽核    	 
    	if(saveFlag)
    	{ 
    	    rtnLimistFlag = limitsOfSave_2();
    	}
    	//暫存紐的稽核    	 
    	else
    	{    	    
    	    rtnLimistFlag = limitsOfSave_1();
    	}
    	
    	return rtnLimistFlag;
    }
    
    /**
     * 查詢即時的診斷資訊
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
                    //取得回傳字串並處理
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
        
        //出院日期為空白,則不寫入電子病歷系統
        //調整若為空白帶現在日期
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
                case 1:  //"01.入院診斷"
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
                case 2: //"02.出院診斷"
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
                case 3:  //"03.主 述":{
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
                case 4:  //"04.病 史":{
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
                case 5:  //"05.體檢發現":{
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
                case 6:  //"06.手術日期及方法":{
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
                case 7:  //"07.住院治療經過":{
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
                case 8:  //"08.合併症":{
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
                case 9:  //"09.檢查記錄":{
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
                case 10:  //"10.放射線報告":{
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
                case 11:  //"11.病理報告":{
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
                case 12:  //"12.其他":{
                    
                    break;
                case 13:  //"13.出院時情形":{
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
                case 14:  //"14.出院指示":{
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
                 //處理更新emrTempDischarge2VO others基本資料
                 //currentVO.setReportStatusScid(saveStatus);
                 //currentVO.setReportDate(new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(eventTimeTextField.getText()).getTime()));

                 //INSERT動作
                 //loadEmrIpdEncounterData(); //load EmrIpdEncounterData to global variable
                 emrTempDischarge2VO.setErdc2Referno(currentVO.encounterNo + currentVO.documentId);//住院序號+病摘流水號
                 
                 //update by leon 2016-02-23(二):FOR改隸
                 String hospitalCode = StringUtil.getHospitalCode();
                 emrTempDischarge2VO.setErdc2Location(hospitalCode);
                 
                 emrTempDischarge2VO.setErdc2Patidtype("I");  //身份證號種類                                
                 emrTempDischarge2VO.setErdc2Patidnumber(emrIpdEncounterVO.getIdNo()); //病患身分證號                 
                 emrTempDischarge2VO.setErdc2Patno(currentVO.getChartNo()); //病歷號 
                 emrTempDischarge2VO.setErdc2Patname(emrIpdEncounterVO.getPatName());//病患姓名
                 emrTempDischarge2VO.setErdc2Sex(emrIpdEncounterVO.getSexType()); //性別
                 
                 emrTempDischarge2VO.setErdc2Patbirth(sdf.format(emrIpdEncounterVO.getBirthDate())); //出生日期
                 emrTempDischarge2VO.setErdc2Patage(new BigDecimal(emrIpdEncounterVO.getAge()));  //病患年齡
                 emrTempDischarge2VO.setErdc2Xxdep(emrIpdEncounterVO.getDeptCode());//出院科別 
                 emrTempDischarge2VO.setErdc2Room(emrIpdEncounterVO.getBedNo());//病床床號
                 emrTempDischarge2VO.setErdc2Indate(sdf.format(emrIpdEncounterVO.getAdmitDate())); //住院日期
                 if(StringUtil.isBlank(emrIpdEncounterVO.getCloseDate())){
                    emrTempDischarge2VO.setErdc2Outdate(sdf.format(Calendar.getInstance().getTime()));
                 }else{
                    emrTempDischarge2VO.setErdc2Outdate(sdf.format(emrIpdEncounterVO.getCloseDate())); //出院日期
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
                     //診斷ICD9：'@' 區分多筆
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
                     //診斷ICD10：'@' 區分多筆                                           
                     String strIcd10 = StringUtil.getIcd10StrEec(emrIpdEncounterVO.getEncounterNo(),emrIpdEncounterVO.getPatState());
                     emrTempDischarge2VO.setErdc2Diaicd9(strIcd10);
                 }                                                 

                 emrTempDischarge2VO.setErdc2Doctoruserid(emrIpdEncounterVO.getVsCode()); //簽章醫師   
                 emrTempDischarge2VO.setErdc2Doctoruserdep(emrIpdEncounterVO.getDeptCode()); // 簽章醫師科別
                 emrTempDischarge2VO.setErdc2Doctoruserid(emrIpdEncounterVO.getVsCode()); // 主治醫師
                 emrTempDischarge2VO.setErdc2Secretcode("1"); //機密碼
                 //emrTempDischarge2VO.setErdc2Secretlv(0); 機密等級 
                 emrTempDischarge2VO.setErdc2Infsource("HIS"); //資料來源 
                 emrTempDischarge2VO.setPostFlag("N");
                                  
                 EmrTempDischarge2DAO.getInstance().insertData(emrTempDischarge2VO);
                 //update by leon 2015-03-06: FOR健保署出摘分享計畫
                 EmrTempDischarge2DAO.getInstance().insertDischgEecData(emrTempDischarge2VO);
             } catch (Exception ex) {
                 MessageManager.showException(ex.getMessage(), ex);
                 logger.debug(ex.getMessage(), ex);
             }
         }  
        
    }
        
    /**
     * 存檔
     * @param saveStatus 1:暫存 /2:完成
     */
    public void saveData(String saveStatus)
    {
        //存檔後切換回"病歷文件"頁籤
        jideTabbedPane.setSelectedIndex(1); //Trigger 切換動作
    	
        if(currentVO != null && !currentVO.getRowStatus().equals("R"))
        {
            try
            {
                //處理附件(圖片)
            	long ckTD01 = System.currentTimeMillis();            	                
                ArrayList al = imageUtil.replaceTemplateImage(htmlViewer.getEkitPanel().getEkitCore().getImageNameList(), currentVO);
                imageUtil.saveImage2DB(al, currentVO);                
                long ckTD02 = System.currentTimeMillis();
                //System.out.println("step saveImage2DB Time:" + (ckTD02 - ckTD01));
                                
                currentVO.setReportStatusScid(saveStatus); //存檔狀態(1:暫存、2:完成)
                currentVO.setReportDate(new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(eventTimeTextField.getText()).getTime())); //報告日期
                currentVO.setLastUpdated(new java.util.Date()); //異動日期
                currentVO.setLastUpdatedBy(GlobalParameters.getInstance().getLoginUserName()); //異動者
                currentVO.setPersonInCharge(this.txtPersonInChargeCode.getText()); //應簽章主治醫師
                
                //update by leon 2012-08-13:治療處置子類別描述
                if("TreNote".equals(filterString))
                {
                    currentVO.setExamOrderDesc(treNoteComboBox.getSelectedItem().toString());                    
                }
                
                //版次
                if (currentVO.getDocumentVersion() != null)
                {
                    Integer version = new Integer(currentVO.getDocumentVersion().intValue() + 1);   //同時間二人以上編輯會有Bug
                    currentVO.setDocumentVersion(version);
                }
                else
                {
                    currentVO.setDocumentVersion(new Integer(1));
                }                                                                                         
                
                //update by leon 2012-04-30:電子病歷，檢核EMR 特殊字元問題
                currentVO.documentText = currentVO.documentText.replaceAll("[\\x01-\\x08\\x0B-\\x0C\\x0E-\\x1F\\x7F-\\x84\\x86-\\x9F]", "");
                
                //replace print error
                currentVO.documentText = currentVO.documentText.replaceAll("bgcolor=\"none\"", "");
                
                //update by leon 2014-02-24: 簽章規則
                //完成(只有主治醫師可以完成)(自行簽章)
                if("2".equals(saveStatus))
                {                	                                        
                    currentVO.setAdviserByScid(GlobalParameters.getInstance().getSecUserVO().getUsername());
                    currentVO.setAdviserByName(GlobalParameters.getInstance().getSecUserVO().getUserRealName());                                       
                }
                //暫存
                else
                {
                    //住院醫師(自行簽章)
                    if("1".equals(saveStatus) && "D2".equals(GlobalParameters.getInstance().getSecUserVO().getUserGroup()))
                    {
                        //update by leon 2014-06-19:setEnteredByScid為簽章平台的撰寫者
                	currentVO.setEnteredByScid(GlobalParameters.getInstance().getSecUserVO().getUsername());
                	currentVO.setEnteredByName(GlobalParameters.getInstance().getSecUserVO().getUserRealName());
                        currentVO.setAdviserByScid(GlobalParameters.getInstance().getSecUserVO().getUsername());
                        currentVO.setAdviserByName(GlobalParameters.getInstance().getSecUserVO().getUserRealName());
                    }
                    //專師(自行簽章)                	
                    else if("1".equals(saveStatus) && "A0".equals(GlobalParameters.getInstance().getSecUserVO().getUserGroup()))
                    {
                        //update by leon 2014-06-19:setEnteredByScid為簽章平台的撰寫者
                	currentVO.setEnteredByScid(GlobalParameters.getInstance().getSecUserVO().getUsername());
                	currentVO.setEnteredByName(GlobalParameters.getInstance().getSecUserVO().getUserRealName());                		
                	currentVO.setAdviserByScid(GlobalParameters.getInstance().getSecUserVO().getUsername());
                        currentVO.setAdviserByName(GlobalParameters.getInstance().getSecUserVO().getUserRealName());
                    }                	
                    //實習醫師(應簽章主治醫師背書)
                    else
                    {
                        //update by leon 2014-06-19:setEnteredByScid為簽章平台的撰寫者
                	currentVO.setEnteredByScid(GlobalParameters.getInstance().getSecUserVO().getUsername());
                	currentVO.setEnteredByName(GlobalParameters.getInstance().getSecUserVO().getUserRealName());                		                		
                	currentVO.setAdviserByScid(currentVO.getPersonInCharge());
                	currentVO.setAdviserByName(currentVO.getPersonInChargeName());
                    }
                }                               
                                                                                              
                //出摘存檔自動更新即時的出院診斷
                //出院日 104-08-10以後啟動                
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
                        //查詢即時的診斷資訊
                        String dataObjectStr = getDataObjectString("DischargeDiagnosis");
                            
                        //取代原有的診斷資訊
                        String tmpDocumentText = currentVO.getDocumentText();
                        int token = tmpDocumentText.indexOf("<02.出院診斷>") + 9;
                        String tmpDocumentText_1 = tmpDocumentText.substring(0, token);
                                                    
                        token = tmpDocumentText.indexOf("</02.出院診斷>");
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
                                            
                //save3:出院病摘送INFINITT電子病歷平台(EEC) AND 健保署   
                if("DischgNote".equals(currentVO.getSheetId()) && "D".equals(currentVO.getReportThirdTypeScid()))
                {
                    saveEmrInterfaceTable();
                }
                
                //save4: 送內部電子簽章平台POHAI
                if(getRptdbFlag())
                {                	
                    doEmrSheet("C");
                }
                
                /**
                 * 0.完成
                 * 1.原病歷量審未過
                 * 2.存檔後病歷量審通過
                 * 3.病患已出院
                 * 4.最後完成者 != 最後主治
                 * 5.通知最後主治可進行簽結了
                 * */
                
                //完成
                if("2".equals(saveStatus))
                {
                    //量審未完成
                    if(getEmrPatNoteErrlogCount() > 0)
                    {
                        //量審                         
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
                                             
                        //量審完成
                        if(getEmrPatNoteErrlogCount() == 0)
                        {
                            //已出院
                            if("D".equals(getPatState()))
                            {
                                //最後一本病歷的權責醫師
                                System.out.println(currentVO.getPersonInCharge());
                                System.out.println(GlobalParameters.getInstance().getEmrIpdEncounterVO().vsCode);
                                System.out.println(currentVO.getPersonInChargeName());
                                if(!currentVO.getPersonInCharge().equals(GlobalParameters.getInstance().getEmrIpdEncounterVO().vsCode))
                                {
                                    System.out.println(currentVO.getPersonInCharge());
                                    System.out.println(GlobalParameters.getInstance().getEmrIpdEncounterVO().vsCode);
                                    System.out.println(currentVO.getPersonInChargeName());
                                    //通知最後主治可進行簽結了
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
                                
                //更新畫面
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
        
    /**update by leon 2014-08-25: 出摘的出院診斷唯讀*/
    public void setDischgDiagReadOnly()
    {    	
    	String _selectedValue = (String)jList1.getSelectedValue();        
        
    	if("02.出院診斷".equals(_selectedValue))
        {
                String _meanData = (String)dischgNoteComboBox.getSelectedItem();                
                _meanData = _meanData.split(":")[0];//DTC                
                
                if("T".equals(_meanData))
                {
                    noteEkitPanel.getEkitPanel().getEkitCore().getTextPane().setEditable(true); //可編輯
                    noteEkitPanel.getEkitPanel().getEkitCore().getTextPane().setBackground(Color.WHITE); //白底
                     noteEkitPanel.getEkitPanel().getEkitCore().jbtnPaste.setEnabled(true);
                }
                else
                {
                    noteEkitPanel.getEkitPanel().getEkitCore().getTextPane().setEditable(false); //不可編輯
                    noteEkitPanel.getEkitPanel().getEkitCore().getTextPane().setBackground(Color.lightGray); //灰底
                    noteEkitPanel.getEkitPanel().getEkitCore().jbtnPaste.setEnabled(false);
                }                
        }
        else
        {        	
        	noteEkitPanel.getEkitPanel().getEkitCore().getTextPane().setEditable(true); //可編輯
        	noteEkitPanel.getEkitPanel().getEkitCore().getTextPane().setBackground(Color.WHITE); //白底
        	noteEkitPanel.getEkitPanel().getEkitCore().jbtnPaste.setEnabled(true);
        }        
    }
        
    private void executeDischgNoteRule(EmrPatNoteVO _currentVO)
    {
    	try
    	{
    		//update by leon 2014-02-19:進入出摘編輯畫面時，顯示目前的子類別
            if("DischgNote".equals(filterString))
            {
        		if(currentVO != null)
        		{            			            		
        			String _selectedItemA = currentVO.getReportThirdTypeScid(); //子類別代碼
        			String _selectedItem = ""; //子類別完整名稱
        			
        			if(StringUtil.isBlank(_selectedItemA))
        			{
        				_selectedItem = dischgNoteComboBoxShowData[0]; //若子類別代碼為空預設為D:出院摘要
        			}
        			else
        			{
        				//子類別代碼不為空以迴圈比對完整名稱
        				for(int i = 0;i<dischgNoteComboBoxShowData.length;i++)
            			{
            				if(dischgNoteComboBoxShowData[i].split(":")[0].equals(_selectedItemA))
            				{
            					_selectedItem = dischgNoteComboBoxShowData[i];
            				}
            			}
        				
        				//若代碼不存在，預設為D:出院摘要
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
	    		
	    		//取得文件名稱
	    		
	    		Calendar calendar_admit = Calendar.getInstance();
	    		calendar_admit.setTime(_admitDate);	    		
	    		
	    		Calendar calendar_now = Calendar.getInstance();
	    		calendar_now.setTime(new Date());	    		
	    			    		
	    		//待修改入院日算 7 + 1天提示
	    		long timeNow = (calendar_now.getTimeInMillis() - calendar_admit.getTimeInMillis());
	    		long mins = timeNow/1000/60;
                        long hr = mins/60;
                        long day = hr/24;
	            
//	    		if(day % 7 == 0)
//	    		{
//	    			noteFormTitleHM.put("t5", "     提醒:今日應撰寫Weekly summary");
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
     * 同步TextPane與SourcePane的資料
     */
    public void syncTextPaneWithSourcePane() {
        if (noteEkitPanel.getEkitPanel().getEkitCore().isSourceWindowActive()) {
            noteEkitPanel.getEkitPanel().getEkitCore().getTextPane().setText(noteEkitPanel.getEkitPanel().getEkitCore().getSourcePane().getText());
        }
    }

    /**
     * 同步HTML Viewer與TextPane的資料
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
            //取得章節內容
            viewText += "<b>" + chaptervo.getNoteChapter() + "</b><BR>" + hm.get(chaptervo.getNoteChapter()).toString() + "<BR>";
        }
        
        refreshViwer(viewText);
    }

    /**
     * 將editor的資料重組並回存至VO
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
                //update by leon 2012-05-28:修改為使用MD5之比對方式
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
     * 初始病歷資料
     * @see
     */
    private void initNoteTextData() {
        hm = new TreeMap();
        
        String viewNoteText = this.getOriginalNoteString();
        //更新章節清單  
        this.refreshChapterList();
        //更新editor
        this.refreshEditor();
        //更新viewer
        this.refreshViwer(viewNoteText);
    }

    /**
     * 將目前在editor編輯的資料重組並回傳
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
     * 將DB的病歷資料根據章節parse至hashMap
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
                //取得章節內容
                String chapterStr = handler.getChapterString(chaptervo.getNoteChapter(), noteText);
               // System.out.println("chapter:"+chaptervo.getNoteChapter());
                if ("OLDHIS".equals(oldStr)) {
                    //chapterStr = chapterStr.replace("<","&lt;");
                    //chapterStr = chapterStr.replace(">","&gt;");
                    //chapterStr = chapterStr.replace("\n","<br>");
                }
                
                 //update by leon 2012-05-31:歷程紀載新增時在PLAN區塊自動帶入病患問題
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
//                                //取得回傳字串並處理
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
                
                //存入存放物件  
                
                //System.out.println("key=" + chaptervo.getNoteChapter());
                hm.put(chaptervo.getNoteChapter(), chapterStr);
                //章節選單List
                chapterOrderArray.add(chaptervo.getNoteChapter());
                chapterHm.put(chaptervo.getNoteChapter(), chaptervo.getPhraseType());
            }
        }

        //    System.out.println(origNoteText);
        return viewNoteText;
    }

    /**
     * 更新章節清單
     * @see
     */
    private void refreshChapterList() {
        //變更畫面
        jList1.setModel(getListModel());

        if (jList1.getModel().getSize() > 0) {
            jList1.setSelectedIndex(0);
            oldSeledtedItem = jList1.getSelectedValue().toString();
        } else {
            oldSeledtedItem = "";
        }
    }

    /**
     * 更新editor
     * @see
     */
    private void refreshEditor()
    {
        //章節清單不為空值
        if(!StringUtil.isBlank(jList1.getSelectedValue()) && !StringUtil.isBlank(hm.get(jList1.getSelectedValue())))
        {
            noteEkitPanel.getEkitPanel().getEkitCore().getTextPane().setText(hm.get(jList1.getSelectedValue()).toString());
            noteEkitPanel.getEkitPanel().getEkitCore().getSourcePane().setText(hm.get(jList1.getSelectedValue()).toString());
        }
        else
        {
            //update by leon 2012-05-09:修正若尚未輸入任何內容即使用病患資料功能或插入圖片無法正常顯示內容
            noteEkitPanel.getEkitPanel().getEkitCore().getTextPane().setText("<p></p>");
            noteEkitPanel.getEkitPanel().getEkitCore().getSourcePane().setText("<p></p>");
        }
    }

    /**
     * 更新viewer
     * @see
     * @param viewNoteText
     */
    private void refreshViwer(String viewNoteText) {
        htmlViewer.getEkitPanel().getEkitCore().getTextPane().setText(viewNoteText);
        htmlViewer.getEkitPanel().getEkitCore().getTextPane().setCaretPosition(0);
        htmlViewer.getEkitPanel().getEkitCore().refreshOnUpdate();
    }

    /**
     * 取得章節清單data model
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
     * 依傳入範本設定各章節資料
     * @see
     * @param noteTemplateString 傳入範本
     * @param isAppend 資料加入模式(覆蓋或添加)
     */
    public void setNoteTemplate(HashMap noteTemplateString, boolean isAppend) {
    	noteEkitPanel.getEkitPanel().getEkitCore().setUpdateFlag(true); //update by leon 2014-02-24:DITTO也算修改
    	
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
     * 設定病歷日期
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
     * 設定處置名稱
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
     * 電子病歷
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
    		//送簽章
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
            //主治醫師完成 或 非主治醫師 暫存 
	    if(("2".equals(currentVO.getReportStatusScid()) || ("1".equals(currentVO.getReportStatusScid()) && !("D1".equals(GlobalParameters.getInstance().getSecUserVO().getUserGroup())))))
	    {
                System.out.println("111");
                if("ProgressNote".equals(filterString))
	        {//病程記載
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
	        {//入院摘要
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
	        {//出院病摘
                    System.out.println("doEmrSheet DischgNote");
	           
	            //20130726 Amanda add.    IER-367 出院摘要上傳HcaEmr電子病歷.PDF
	            DischgNoteEmrPdfSheet dischgNotePdfSheet = new DischgNoteEmrPdfSheet();
	            dischgNotePdfSheet.documentId = currentVO.getDocumentId();
	            dischgNotePdfSheet.setEmrPatNoteVO(currentVO);
	            dischgNotePdfSheet.setEmrIpdEncounterVO(GlobalParameters.getInstance().getEmrIpdEncounterVO());
	            dischgNotePdfSheet.setChapterDataMap(hm);
	            dischgNotePdfSheet.setChapterList(emrNoteChapterData);
	            //dischgNotePdfSheet.setReport(new DisChgReport());
	            dischgNotePdfSheet.setReport(new DisChgReport(currentVO, true));                
	            dischgNotePdfSheet.setActionType(actionType);
	                
                    System.out.println("ＡＡＡ");
                    dischgNotePdfSheet.doProcess();
	            System.out.println("doEmrSheet DischgNote END");
                }
	        //update by leon 2014-07-24:手術紀錄單
	        else if("OpNote".equals(filterString))
	        {
                    System.out.println("doEmrSheet OrReport");
                                    
                    //update by leon 2015-06-12: 手術紀錄單未完成不送簽
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
                            //查詢OER_PATBAS                            
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
 * changeTextPanel:切換TAB執行之動作
 * checkOutDocument:佔用currentVO的編輯權限
 * checkInDocument:釋放currentVO的編輯權限
 * getIPAddress:取得IP位址
 * getDutyDocInfo:取得ICU當班醫師
 * getCommNeedFlagByDate:稽核當日PROGRESS NOTE是否需要被COMMAND
 * getRptdbFlag:取得是否送簽院內簽章FLAG
 * limitsOfSave_2:完成之稽核
 * limitsOfSave_1:暫存之稽核
 * tempSave:暫存
 * rollBack:復原
 * */
//***funtion list*************************************************************//
 
    /**
     * 取得currentVO
     * */
    public EmrPatNoteVO getCurrentVO(long documentId)
    {
        System.out.println("[getCurrentVO][Begin]");
        
        EmrPatNoteVO vo = new EmrPatNoteVO();
        
        //建立JDBC連線
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
     * 按下復原按鈕執行之動作
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
                this.checkInDocument(); //update by leon 2015-04-02: 釋放currentVO的編輯權限
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
     * tab切換畫面動作
     * */
    public void changeTextPanel(int status)
    {
        //病患清單
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
        //病歷文件
        else if (status == 1)
        {  
            syncTextPaneWithSourcePane();
            syncViewerWithTextPane();
            saveEditorText2Row();
            ((CardLayout) jPanel2.getLayout()).show(jPanel2, "htmlViewer");
        }
        //章節索引
        else if (status == 2)
        {
            //自動新增
            if(currentVO == null)
            {
                if (addBtn.isEnabled())
                {
                    addBtn.doClick();
                }
                //手術不可新增
                else
                {
                    return;
                }
            }
            
            //重新讀取病患資料物件變數
            EmrNoteCategoryVO vo = (EmrNoteCategoryVO)((HCComboBoxModel)noteCategoryComboBox.getModel()).getVO();            
            //切換頁籤後，預設選擇第一個章節
         
            //歷程記載切換到章節索引的編輯模式, 預設為教學意見章節
            if("ProgressNote".equals(filterString))
            {
                jList1.setSelectedIndex(5);
            }
             
            //若為空值,進行預設HTML處理
            if(!StringUtil.isBlank(hm.get(jList1.getSelectedValue())))
            {
                noteEkitPanel.getEkitPanel().getEkitCore().getTextPane().setText(hm.get(jList1.getSelectedValue()).toString());
            }
            else
            {
                //update by leon 2012-05-09:修正若尚未輸入任何內容即使用病患資料功能或插入圖片無法正常顯示內容
                noteEkitPanel.getEkitPanel().getEkitCore().getTextPane().setText("<p></p>");
            }
         
            syncTextPaneWithSourcePane();
         
            //切換頁簽時帶入應簽章醫師資訊
            txtPersonInChargeName.setText(currentVO.getPersonInChargeName());
            txtPersonInChargeCode.setText(currentVO.getPersonInCharge());
         
            oldSeledtedItem = jList1.getSelectedValue().toString();
            oldSeledtedIndex = jList1.getSelectedIndex();
            noteDataObjectPanel.loadDataobjectData(vo.getId().toString(), oldSeledtedItem);
            noteDataObjectPanel.setNoteCategory(filterString);
            //將章節名稱顯示在編輯區的TITLE上
            outsideNoteEkitPanel.setBorder(BorderFactory.createTitledBorder(jList1.getSelectedValue().toString()));
         
            ((CardLayout) jPanel2.getLayout()).show(jPanel2, "noteEkitPanel");
         
            //update by leon 2015-03-30: 設定出摘的出院診斷章節唯讀, 暫時註解
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
     * 取得執行程式電腦IP位址
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
     * 取得ICU當班醫師
     * */
    public HashMap getDutyDocInfo(String encounterNo)
    {
        System.out.println("[getDutyDocInfo][Begin][encounterNo: "+ encounterNo +"]");
    	
	HashMap dutyDocInfo = new HashMap();
    	
        //建立JDBC連線
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
     * 判斷當日是否需要被COMMENT
     * */
    public boolean getCommNeedFlagByDate(String encounterNo, String reportDate)
    {
    	System.out.println("[getCommNeedFlagByDate][Begin][encounterNo: "+ encounterNo +"][reportDate: "+ reportDate +"]");
    	
    	boolean bolCommNeedFlag = true; //當天是否需要COMMENT，預設為需要
    	
        //建立JDBC連線
    	Statement stmt = null;
        ResultSet rs = null;
        Connection conn;
        
        try
        {        	
            conn = DBConnectionHelper.getInstance().getMySqlConnection();
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            	
            //查詢當日已被COMMENT的病歷數量
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
            	//若當日已有被COMMENT過的紀錄
                if(rs.getInt("commNeedFlag") > 0)
                {
                    //當日的其他病歷不需再被COMMENT
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
     * 判斷RPTDB是否存活
     * */
    public boolean getRptdbFlag()
    {
        //System.out.println("[getRptdbFlag][Begin]");
    	
    	boolean result = true;
    	
        //建立JDBC連線
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
     * 完成之稽核
     * */
    public boolean limitsOfSave_2()
    {
        boolean rtnLimistFlag = true;
     
        //稽核1: 非主治醫師不可完成病歷
        if(!"D1".equals(GlobalParameters.getInstance().getSecUserVO().getUserGroup()))
        {
            String showMsg = "非主治醫師，僅能使用『暫存鈕』進行存檔!";
            JOptionPane.showMessageDialog(this, showMsg);
            rtnLimistFlag = false;
        }
     
        //稽核2: 出院摘要必須要病人的狀態為醫師允許出院才可以完成(確保病歷上的出院日期不為空值)
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
            {//出摘
                if("A".equals(_patState))
                {//在院中
                    String showMsg = "出院摘要需病患狀態為醫師允許出院以後才可完成(目前狀態:在院中)";
                    JOptionPane.showMessageDialog(this, showMsg);
                    rtnLimistFlag = false;
                }
            }                                       
        }
     
        //稽核3: 歷程記載需至少兩天COMMENT一次
        if(rtnLimistFlag)
        {                       
            //預設為不需要COMMENT
            boolean runSetCommNeedFlag = false;
             
            //判斷是否為progressnote            
            if("progressnote".equals(currentVO.getReportSubtypeScid()))     
            {                               
                //update by leon 2014-06-17: 主治醫師新增無法直接完成BUG修正
                try
                {
                    currentVO.setReportDate(new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(eventTimeTextField.getText()).getTime())); //報告日期
                }
                catch(Exception e)
                {
                    String showMsg = "病歷日期不可為空";
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
                    String showMsg = "首日不用判斷，可存檔";
                    JOptionPane.showMessageDialog(this, showMsg);
                    */
                    if(noteEkitPanel.getDataHasChangedStatus())
                    {
                        currentVO.setCommNeedFlag("N");
                    }
                }
                else
                {
                    //昨天需要COMMENT
                    if(getCommNeedFlagByDate(currentVO.getEncounterNo(),yesReportDateStr))
                    {
                        //今天需COMMENT
                        if(getCommNeedFlagByDate(currentVO.getEncounterNo(),todReportDateStr))
                        {
                            //未有異動內容，不可完成
                            if(!noteEkitPanel.getDataHasChangedStatus())
                            {
                                String showMsg = "至少兩天撰寫一次VS COMMENT。";
                                JOptionPane.showMessageDialog(this, showMsg);
                                rtnLimistFlag = false;
                            }
                            //有異動內容，可完成
                            else
                            {                                                       
                                /*
                                String showMsg = "有異動內容，可存檔";
                                JOptionPane.showMessageDialog(this, showMsg);
                                */
                                currentVO.setCommNeedFlag("N");                                               }
                            }
                        //今天已有COMMENT過，可存檔
                        else
                        {                                               
                            /*
                            String showMsg = "今天已有COMMENT過，可存檔";
                            JOptionPane.showMessageDialog(this, showMsg);
                            */
                            if(noteEkitPanel.getDataHasChangedStatus())
                            {
                                currentVO.setCommNeedFlag("N");
                            }
                        }
                    }
                    //昨天已COMMENT，今天不用判斷
                    else
                    {                                       
                        /*
                        String showMsg = "昨天已COMMENT，今天不用判斷";
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
     
        //稽核4: 出摘只能存一份
        if(rtnLimistFlag)
        {                                       
            if("dischgnote".equals(currentVO.getReportSubtypeScid()))
            {                                       
                if("D".equals(currentVO.getReportThirdTypeScid()))
                {                                               
                    int intDischgnoteCount = 0;
                    StringBuffer queryEmrPatNoteSQL = new StringBuffer();
                    ArrayList<Map> list;
                                                                                                 
                    //新增
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
                        String showMsg = "一個病人只能有一份出摘";
                        JOptionPane.showMessageDialog(this, showMsg);
                        rtnLimistFlag = false;
                    }
                }
            }
        }
        
        //稽核5:如果有被CHECK OUT, 登入者需為同一人才可以完成
        if(rtnLimistFlag)
        {
            FunctionUtil functionUtil = new FunctionUtil();
            String editByCode = functionUtil.getEditByCode(currentVO.getId());
            
            if(!("N".equals(editByCode)))
            {
                if(!(editByCode.equals(GlobalParameters.getInstance().getSecUserVO().getUsername())))
                {
                    String editByName = functionUtil.getEditByName(editByCode);
                    String showMsg = "此份病歷資料目前由<"+ editByName +">編輯中";
                    JOptionPane.showMessageDialog(this, showMsg);
                    rtnLimistFlag = false;
                }                
            }
        }
                        
        //稽核6:出摘或入摘, 各章結內容不得為空才可完成        
        if(rtnLimistFlag)
        {
            //出摘類或者入摘
            if("D".equals(currentVO.getReportThirdTypeScid())
                || "C".equals(currentVO.getReportThirdTypeScid())
                || "adminnote".equals(currentVO.getReportSubtypeScid()))
            {                
                //取得在編輯區尚未存檔的文件資料
                String tmpDocumentText = getNewNoteString();                                                                            
                
                //逐一章節稽核
                String showMsg = "";                
                Iterator it = hm.keySet().iterator();
                while (it.hasNext())
                {
                    //章節名稱
                    String key = it.next().toString();                    
                    
                    if(!"12.教學意見".equals(key) && !"12.其他".equals(key))
                    {
                        //章節起始位置
                        int tokenS = tmpDocumentText.indexOf("<"+ key +">");
                        tokenS = tmpDocumentText.indexOf(">",tokenS)+1;                    
                        
                        //章節結束位置
                        int tokenE = tmpDocumentText.indexOf("</"+ key +">");                    
                        
                        //章節內容
                        String tmpStr = tmpDocumentText.substring(tokenS, tokenE);
                                            
                        tmpStr = tmpStr.replaceAll("<.*?>", ""); //去除HTML TAG
                        tmpStr = tmpStr.trim(); //去除空白
                        
                        //若未填寫內容卡控不允許為空
                        if(tmpStr.length()==0)
                        {
                            showMsg = showMsg + (key + "\n");
                            rtnLimistFlag = false;
                        }
                    }
                }
                
                if(rtnLimistFlag==false)                
                {
                    JOptionPane.showMessageDialog(this, "下列章節不允許為空:\n" + showMsg);
                }                
            }
        }        
        
        //稽核7:手術報報, 稽核手術記錄單未完成不可完成手術報告        
        if(rtnLimistFlag)
        {
            if("opnote".equals(currentVO.getReportSubtypeScid()))
            {
                OrReport orReport = new OrReport(currentVO.getSheetXmlMeta());
                if(!("Y".equals(orReport.getOrVo().feeOpFlag)))
                {
                    String showMsg = "先完成手術紀錄單，才可完成手術報告，請先暫存";
                    JOptionPane.showMessageDialog(this, showMsg);
                    rtnLimistFlag = false;
                }
            }
        }
        
        return rtnLimistFlag;
    }
    
    /**
     * 暫存之稽核
     * */
    public boolean limitsOfSave_1()
    {
        boolean rtnLimistFlag = true;
        
        //稽核1: 已完成之病歷不可再暫存
        if("2".equals(currentVO.getReportStatusScid()))
        {
            //update by leon 2014-02-25:已完成不可再使用暫存，提示後不要清空資料
            //rollBack();
            String showMsg = "已完成不可再使用暫存。";
            JOptionPane.showMessageDialog(this, showMsg);
            rtnLimistFlag = false;
        }
                                            
        //稽核2: 未異動內容不允許暫存
        if(rtnLimistFlag)
        {
            if(noteEkitPanel.getDataHasChangedStatus())
            {
                //主治醫師已COMMENT，專師或住院醫師再去修改，不用重複COMMENT
                if("N".equals(currentVO.getCommNeedFlag()))
                {
                    currentVO.setCommNeedFlag("N");
                }
                //主治醫師未COMMENT
                else
                {
                    //專師或住院醫師暫存，依然需要被COMMENT
                    if(!"D1".equals(GlobalParameters.getInstance().getSecUserVO().getUserGroup()))
                    {
                        currentVO.setCommNeedFlag("Y");
                    }
                    //主治醫師暫存，註記為已COMMENT
                    else
                    {
                        currentVO.setCommNeedFlag("N");
                    }
                }
            }
            else
            {
                String showMsg = "未異動任何內容，不允許暫存。";
                JOptionPane.showMessageDialog(this, showMsg);
                rtnLimistFlag = false;
            }
        }
            
        //稽核3: 出摘只能存一份
        if(rtnLimistFlag)
        {
            if("dischgnote".equals(currentVO.getReportSubtypeScid()))
            {
                if("D".equals(currentVO.getReportThirdTypeScid()))
                {
                    int intDischgnoteCount = 0;
                    StringBuffer queryEmrPatNoteSQL = new StringBuffer();
                    ArrayList<Map> list;
                                    
                    //新增
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
                        String showMsg = "一個病人只能有一份出摘";
                        JOptionPane.showMessageDialog(this, showMsg);
                        rtnLimistFlag = false;
                    }
                }
            }
        }
        
        //稽核4: 自動產生之病歷不允許異動        
        if(rtnLimistFlag)
        {
            if("012004-1".equals(currentVO.getCreatedBy()))
            {
                if(!currentVO.getReportDateStr().equals(eventTimeTextField.getText()))
                {
                    String showMsg = "系統依規範自動建立之必要病歷，不得變更病歷日期";
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
                        String showMsg = "系統依規範自動建立之必要病歷，不得變更病歷子類別";
                        JOptionPane.showMessageDialog(this, showMsg);
                        rtnLimistFlag = false;
                    }                    
                }
                else if(currentVO.getReportSubtypeScid().equals("dischgnote"))
                {
                    /*
                    if(dischgNoteComboBox.getSelectedItem().toString().equalsIgnoreCase(currentVO.getReportThirdTypeCode()))
                    {
                        String showMsg = "系統依規範自動建立之必要病歷，不得變更病歷子類別";
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
     * 暫存按鈕動作
     * */
    public void tempSave()
    {
        if (currentVO != null)
        {
            try
            {                
                //存檔前稽核
                if(limitsOfSave(false) == false)
                {
                    return;
                }
            
                //存檔前稽核2
                checkData2();
                
                //修正
                fixDocumentText();
                    
                //存檔(rule:一但完成後就不可再暫存)
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
     * 導頁診斷維護
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
            
            
            /**博愛*/
            //測試機
            if("test".equalsIgnoreCase(aEnvArg))
            {
                strUrl.append("http://testhisapp:8180/PohaiServer/MainSocket.html");
            }
            //正式機
            else if("production".equalsIgnoreCase(aEnvArg))
            {                
                strUrl.append("http://hisapp:8080/PohaiServer/MainSocket.html");
            }            

            strUrl.append(("#user=" + strUser)); //帳號
            strUrl.append(("&password=" + strPassword)); //密碼
            strUrl.append(("&branch=" + strBranch)); //登入地點 EX:03D0
            strUrl.append("&loginType=IER");
            strUrl.append("&moduleId=IerMain");
            strUrl.append("&tabId=Ier2020");
            strUrl.append("&pageId=Ier2020Page");
            strUrl.append(("&strEncounterNo=" + GlobalParameters.getInstance().getEncounterNo())); //住院序號
            strUrl.append(("&strChartNo=" + GlobalParameters.getInstance().getChartNo())); //病歷號    
                        
            System.out.println(strUrl.toString());                                                
            //Chromium開啟
            Runtime.getRuntime().exec(new String[] {"C:\\Chromiumhis\\bin\\chrome.exe", strUrl.toString()});
            //本機測試
            //Runtime.getRuntime().exec(new String[] {"D:\\chrlauncher-win64-stable-codecs-sync\\bin\\chrome.exe", strUrl.toString()});
            System.out.println("[directToDiagPage][END]");
        }
        catch(Exception ex)
        {
            try
            {
                //32位元IE開啟
                Runtime.getRuntime().exec(new String[] {"C:\\Program Files (x86)\\Internet Explorer\\iexplore.exe", strUrl.toString()});                
            }
            catch(Exception ee)
            {
                try
                {
                    //64位元IE開啟
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
     * 取得病患狀態
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
     * 查詢量審未過筆數
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
     * 如果系統等於OR
     * Document_No有值             
     * 該筆資料已完成
     * emr_pat_note_log >> count 0
     * 送簽
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
     * 版本比對
     * */
    public void doDiffPdf()
    {
        System.out.println("[doDiffPdf][Start]");
        //取得點選的document id
        String documentId = currentVO.getDocumentId();
        
        Connection conn;
        Statement stmt = null;
        ResultSet rs = null;                                
        boolean checkFlag = true;
                
        try
        {               
            System.out.println("查詢hcaemr取得主治醫師最後版");
            //查詢hcaemr取得主治醫師最後版
            conn = DBConnectionHelper.getInstance().getMySqlConnection();            
            System.out.println("查詢hcaemr取得主治醫師最後版");
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);            
            System.out.println("查詢hcaemr取得主治醫師最後版");
                            
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
            System.out.println("查詢hcaemr取得主治醫師最後版");
            if(rs.next())
            {                   
                System.out.println("查詢hcaemr取得主治醫師最後版");
                downFile(rs.getString("document_id"),"vs");
                System.out.println("查詢hcaemr取得主治醫師最後版");
            }
            else
            {
                checkFlag = false;
                JOptionPane.showMessageDialog(this, "主治醫師未撰寫任何版本");                
            }
            
            System.out.println("查詢hcaemr取得非主治醫師最後版");
            //查詢hcaemr取得非主治醫師最後版
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
                JOptionPane.showMessageDialog(this, "專師/住院醫師未撰寫任何版本");
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
                
        //比對2.X                
        if(checkFlag)
        {
            String aEnvArg = GlobalParameters.getInstance().getEnvArg().toString();
            
            
            /**博愛測試機&正式機*/
            executeCommand("\\\\nt-svr2\\使用者桌面捷徑\\電子病歷比對\\diffpdf -w D:\\apn.pdf D:\\vs.pdf");
            
            
            /**慈濟測試機&各環境
            executeCommand("C:\\diffpdf -w C:\\apn.pdf C:\\vs.pdf");            
            */
        }        
        
        //比對5.X
        //executeCommand("C:\\Program Files\\Qtrac\\DiffPDF\\diffpdf ");
        System.out.println("[doDiffPdf][End]");
    }
    
    /**
     * 執行CMD
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
                                
            /*已簽章版
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
            
            //未簽章版
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
                
                //未簽
                if("N".equals(rs.getString("sign_status")))
                {
                    //FileServer;/sourceDocu/IER/0000000/20160204008476_20160204155832.PDF
                    remotePath = bb[1] +"/"+ bb[2] +"/"+ bb[3];
                    fileName = bb[4];
                }
                //已簽
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
                //代簽
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
                //代簽
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
            //取得檔案
            byte[] data = getDataFromFtp(url,username,password, remotePath, fileName);            
            
            //已簽章版為XML轉換成PDF
            String pdfContent = "";
            if(!"N".equals(signStatus))
            {
                Document _document = DocumentHelper.parseText(new String(data,"UTF-8").trim());
                Element _root = _document.getRootElement();
                pdfContent = _root.element("pdfContent").getText();                
            }
            
            //另存PDF            
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
            //如果系統等於OR
            /*
            String tempEncounterNo = GlobalParameters.getInstance().getEncounterNo();
            String tempSys = GlobalParameters.getInstance().getSys();
            String tempDocumentNo = GlobalParameters.getInstance().getDocumentNo();
            */
            
            if("OR".equalsIgnoreCase(tempSys))
            {
                //Document_No有值
                if(tempDocumentNo != null)
                {
                    //該筆資料已完成
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
     * 測試程式
     * @param params
     */
    public static void main(String[] params)
    {
        UIUtils.setNoteLookAndFeel();
        //------測試個案-----                                                
        GlobalParameters.getInstance().setEncounterNo("I20090709");//I20030002
        GlobalParameters.getInstance().setChartNo("0614161");//0000000
        GlobalParameters.getInstance().setLoginUserName("012004");//醫師I20090709
        
        /*
        GlobalParameters.getInstance().setEncounterNo("O18041201494");
        GlobalParameters.getInstance().setChartNo("0705327");
        GlobalParameters.getInstance().setLoginUserName("4066");//醫師        
        GlobalParameters.getInstance().setDocumentNo("OOR-180400194");        
        */
        
        GlobalParameters.getInstance().setSys("");//OR
        GlobalParameters.getInstance().setDocType("VS");//APN；VS
        GlobalParameters.getInstance().setUseVSTemplate("Y");        
        GlobalParameters.getInstance().setEnvArg("production"); //production / test
            
        System.out.println(GlobalParameters.getInstance().getEnvArg());
        
        JFrame jf = new JFrame();
        jf.setSize(1280, 900);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.getContentPane().setLayout(new BorderLayout());    
        NoteMainPanel panel = new NoteMainPanel(jf);        
        jf.getContentPane().add(panel, BorderLayout.CENTER);        
        //panel.setFormTitleHM.put("t1", "病歷撰寫系統");
        panel.refreshFormTitle();
        
        jf.setVisible(true);
    }
}