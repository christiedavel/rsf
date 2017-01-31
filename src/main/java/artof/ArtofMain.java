package artof;

import artof.database.Archiver;
import artof.database.ArtofDB;
import artof.database.BusPrefDets;
import artof.database.DesignDets;
import artof.designer.Designer;
import artof.designitems.DesignBoard;
import artof.designitems.DesignFoil;
import artof.designitems.DesignFrame;
import artof.designitems.DesignGlassAndBack;
import artof.designitems.DesignSlip;
import artof.dialogs.AboutDialog;
import artof.dialogs.Artists2;
import artof.dialogs.BusPreferences;
import artof.dialogs.Clients2;
import artof.dialogs.InternetRegDialog;
import artof.dialogs.Introduction;
import artof.dialogs.MethodPreferences;
import artof.dialogs.OpenDesign;
import artof.dialogs.OwnerDialog;
import artof.dialogs.ProgressDialog;
import artof.dialogs.RegistrationDialog;
import artof.dialogs.RestoreDialog;
import artof.materials.MaterialClient;
import artof.materials.MaterialDets;
import artof.materials.MaterialDialog;
import artof.materials.RemoveDialog;
import artof.materials.SupplierDialog;
import artof.materials.TypeMapper;
import artof.utils.SettingDialog;
import artof.utils.UserSettings;
import artof.utils.UserSettings2;
import artof.utils.Utils;
import com.incors.plaf.kunststoff.KunststoffLookAndFeel;
import com.incors.plaf.kunststoff.KunststoffTheme;

import javax.help.CSH;
import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.swing.*;
import java.awt.*;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;



/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public class ArtofMain /*extends JFrame*/ {
  //private ArtofMain frame = this;
  private JFrame frame = new JFrame();
  private artof.database.ArtofDB db_conn;
  //private KunststoffLookAndFeel kunststoffLnF;
  private KunststoffLookAndFeel kunststoffLnF;
  private boolean stufferCheck = false;
  private boolean deletionCheck = false;
  private boolean expiryCheck = false;
  private Designs des;
  private boolean startupComplete = false;
  private JLabel lblMUDiscount = new JLabel();
  BorderLayout borderLayout1 = new BorderLayout();
  JToolBar toolBar = new JToolBar();

  JButton btnNewDesign = new JButton(new ImageIcon("images/NewDesign.gif"));
  JButton btnOpenDesign = new JButton(new ImageIcon("images/OpenDesign.gif"));
  JButton btnCloseDesign = new JButton(new ImageIcon("images/CloseDesign.gif"));
  JButton btnSaveDesign = new JButton(new ImageIcon("images/SaveDesign.gif"));
  JButton btnSaveAllDesigns = new JButton(new ImageIcon("images/SaveAllDesigns.gif"));

  JButton btnDelete = new JButton(new ImageIcon("images/Delete.gif"));
  JButton btnAddArtwork = new JButton(new ImageIcon("images/ArtAdd.gif"));
  JButton btnAddBoard = new JButton(new ImageIcon("images/BoardAdd.gif"));
  JButton btnInsertBoard = new JButton(new ImageIcon("images/BoardInsert.gif"));
  JButton btnInsertFoil = new JButton(new ImageIcon("images/FoilInsert.gif"));
  JButton btnInsertSlip = new JButton(new ImageIcon("images/SlipInsert.gif"));
  JButton btnAddGlass = new JButton(new ImageIcon("images/GlassAdd.gif"));
  JButton btnInsertFrame = new JButton(new ImageIcon("images/FrameInsert.gif"));
  JButton btnAddFrame = new JButton(new ImageIcon("images/FrameAdd.gif"));
  JButton btnAddBack = new JButton(new ImageIcon("images/BackAdd.gif"));
  JButton btnCreateBox = new JButton(new ImageIcon("images/CreateBox.gif"));
  JButton btnPrevDesign = new JButton(new ImageIcon("images/PrevDes.gif"));
  JButton btnNextDesign = new JButton(new ImageIcon("images/NextDes.gif"));
  JButton btnFindDesign = new JButton(new ImageIcon("images/FindDesign.gif"));

  JButton btnPrint = new JButton(new ImageIcon("images/Print.gif"));
  JButton btnAllocateStock = new JButton(new ImageIcon("images/Stocker.gif"));
  JButton btnOffCuts = new JButton(new ImageIcon("images/CutList.gif"));

  public ArtofMain() {
    ShowIntro introDude = new ShowIntro();
    initializeLookAndFeel();
    UserSettings2.readUserSettings();

    boolean foundStufferFile = false;
    String regno = "78945584249";
    try {
      BufferedReader in = new BufferedReader(new FileReader("images/stuffer.gif"));
      regno = in.readLine();
      in.close();
      if (regno == null) {
        throw new Exception();
      }

      foundStufferFile = true;

    } catch (Exception e) {
      try {
        BufferedWriter out = new BufferedWriter(new FileWriter("images/stuffer.gif"));
        Random r = new Random(Utils.getCurrentTime());
        String mes = String.valueOf(r.nextInt(9));
        mes += String.valueOf(r.nextInt(9));
        mes += String.valueOf(r.nextInt(9));
        mes += String.valueOf(r.nextInt(9));
        mes += String.valueOf(r.nextInt(9));
        mes += String.valueOf(r.nextInt(9));
        mes += String.valueOf(r.nextInt(9));
        mes += String.valueOf(r.nextInt(9));
        mes += String.valueOf(r.nextInt(9));
        mes += String.valueOf(r.nextInt(9));
        out.write(mes);
        out.close();
        regno = mes;
        foundStufferFile = false;

      } catch (Exception e2) {
        foundStufferFile = false;
      }
    }

    boolean userSettingsPresent = true;
    if (!UserSettings.readUserSettings()) {
      userSettingsPresent = false;
      if (!foundStufferFile) {
        UserSettings.registrationNo = regno;
      }
      UserSettings.expiryDate = Utils.getEersteVervalDatum();
      UserSettings.saveUserSettings();
    }

    try {
      db_conn = ArtofDB.getCurrentDB();
      if (db_conn == null)
        throw new Exception();

    } catch (Exception e) {
      String mes = "Failed to make a connection to the database specified.  ";
      mes += "The database path can be edited on Settings | Design Settings.";
      JOptionPane.showMessageDialog(frame, mes, "Error", JOptionPane.ERROR_MESSAGE);
    }

    try {
      stufferCheck = db_conn.checkStuffers();
    }
    catch (NullPointerException e) {
      stufferCheck = false;
    }

    try {
      if (UserSettings.registrationNo.equals(regno)) {
        deletionCheck = true;
      }
      else {
        deletionCheck = false;
      }
    }
    catch (NullPointerException e) {
      deletionCheck = true;
    }

    // check of hele rsf directory gecopy is
//    PCSec pcSec = PCSec.getInstance();
//    int stufferCount = db_conn.countStuffers() - 1;
//    boolean pcFile = pcSec.validate(foundStufferFile, userSettingsPresent, stufferCount, regno);
//    if (!pcFile) {
//      String mes = Designs.getRegistrationNotValid();
//      JOptionPane.showMessageDialog(frame, mes, Designs.getRegistration(), JOptionPane.ERROR_MESSAGE);
//
//    } else {
//      int dae = UserSettings.expiryDate - Utils.getCurrentDate();
//      if (dae < 0) {
//        String mes = ArtofDB.getExpired();
//        JOptionPane.showMessageDialog(frame, mes, Designs.getRegistration(), JOptionPane.WARNING_MESSAGE);
//        expiryCheck = false;
//
//      }
//      else if (dae == 0) {
//        String mes = ArtofDB.getExpiredo();
//        JOptionPane.showMessageDialog(frame, mes, Designs.getRegistration(), JOptionPane.WARNING_MESSAGE);
//        expiryCheck = true;
//
//      }
//      else if (dae == 1) {
//        String mes = ArtofDB.getExpiredi();
//        JOptionPane.showMessageDialog(frame, mes, Designs.getRegistration(), JOptionPane.WARNING_MESSAGE);
//        expiryCheck = true;
//
//      }
//      else if (dae <= 5 && dae > 1) {
//        String mes = ArtofDB.getExpiredx(dae);
//        JOptionPane.showMessageDialog(frame, mes, Designs.getRegistration(), JOptionPane.WARNING_MESSAGE);
//        expiryCheck = true;
//
//      }
//      else {
//        expiryCheck = true;
//      }
//    }

    // stel al die maps op van materials
    try {
      DesignBoard.createBoardMapper();
      DesignFrame.createFrameMapper();
      DesignSlip.createSlipMapper();
      DesignFoil.createFoilMapper();
      DesignGlassAndBack.createGBMapper();

      TypeMapper.buildTypeMapper();

    }
    catch (NullPointerException e) {
      e.printStackTrace();
    }

    bouToolBar();
    frame.getContentPane().setLayout(new BorderLayout());
    frame.getContentPane().add(toolBar, BorderLayout.NORTH);
    des = new Designs(db_conn);
    frame.getContentPane().add(des, BorderLayout.CENTER);
    JMenuBar menuBar = new Menubar();
    frame.setJMenuBar(menuBar);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    lblMUDiscount.setText(String.valueOf(BusPrefDets.getMUDiscSelected() + 1));
    lblMUDiscount.setForeground(Color.darkGray);

    // stel markup for discount
    frame.getToolkit().addAWTEventListener(new AWTEventListener() {
      public void eventDispatched(AWTEvent e) {
        try {
          KeyEvent dude = (KeyEvent) e;
          if (dude.getKeyCode() == KeyEvent.VK_F1) {
            BusPrefDets.setMUDiscSelected(0);
            des.btnCalculate2_actionPerformed();
            des.updateTableData();
          }
          else if (dude.getKeyCode() == KeyEvent.VK_F2) {
            BusPrefDets.setMUDiscSelected(1);
            des.btnCalculate2_actionPerformed();
            des.updateTableData();
          }
          else if (dude.getKeyCode() == KeyEvent.VK_F3) {
            BusPrefDets.setMUDiscSelected(2);
            des.btnCalculate2_actionPerformed();
            des.updateTableData();
          }
          else if (dude.getKeyCode() == KeyEvent.VK_F4) {
            BusPrefDets.setMUDiscSelected(3);
            des.btnCalculate2_actionPerformed();
            des.updateTableData();
          }
          else if (dude.getKeyCode() == KeyEvent.VK_F5) {
            BusPrefDets.setMUDiscSelected(4);
            des.btnCalculate2_actionPerformed();
            des.updateTableData();
          }
          else if (dude.getKeyCode() == KeyEvent.VK_F6) {
            BusPrefDets.setMUDiscSelected(5);
            des.btnCalculate2_actionPerformed();
            des.updateTableData();
          }
          lblMUDiscount.setText(String.valueOf(BusPrefDets.getMUDiscSelected() + 1));

        }
        catch (ClassCastException x) {
          // doen net mooi fokkol
        }
      }
    }

    , AWTEvent.KEY_EVENT_MASK
        );

    frame.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        UserSettings.saveUserSettings();
        UserSettings2.saveUserSettings();
        des.checkModifiedDesigns();
        try {
          ArtofDB.getCurrentDB().closeConnection();
        } catch (NullPointerException ee) {
          // doen fokkol
        }
      }
    });

    // Disable al die toolbars as die databasis konneksie nie reg is nie
//    if (!stufferCheck || !deletionCheck || !expiryCheck || !pcFile || db_conn == null || !pcFile) {
	  if (db_conn == null ) {
      for (int i = 0; i < toolBar.getComponentCount(); i++) {
        toolBar.getComponentAtIndex(i).setEnabled(false);
      }
    }

    Dimension screenSize = frame.getToolkit().getScreenSize();
    frame.setSize(new Dimension(screenSize.width, screenSize.height));
    frame.setLocation(screenSize.width / 2 - frame.getWidth() / 2,
                screenSize.height / 2 - frame.getHeight() / 2);
    frame.setExtendedState(JFrame.MAXIMIZED_HORIZ);
    frame.setTitle("RSF");
    startupComplete = true;
    frame.setVisible(true);
  }

  private void bouToolBar() {
    toolBar.add(lblMUDiscount);
    toolBar.addSeparator();

    toolBar.add(btnNewDesign);
    toolBar.add(btnOpenDesign);
    toolBar.add(btnCloseDesign);
    toolBar.add(btnSaveDesign);
    toolBar.add(btnSaveAllDesigns);

    toolBar.addSeparator();
    toolBar.add(btnAddArtwork);
    toolBar.add(btnAddBoard);
    toolBar.add(btnAddFrame);
    toolBar.add(btnAddGlass);
    toolBar.add(btnAddBack);
    toolBar.add(btnCreateBox);
    toolBar.addSeparator();
    toolBar.add(btnInsertBoard);
    toolBar.add(btnInsertFrame);
    toolBar.add(btnInsertSlip);
    toolBar.add(btnInsertFoil);
    toolBar.addSeparator();
    toolBar.add(btnDelete);

    toolBar.addSeparator();
    toolBar.add(btnPrevDesign);
    toolBar.add(btnNextDesign);
    toolBar.add(btnFindDesign);

    toolBar.add(btnPrint);
    toolBar.add(btnAllocateStock);
    toolBar.add(btnOffCuts);

    btnNewDesign.setToolTipText("Create New Design");
    btnOpenDesign.setToolTipText("Open existing Designs");
    btnCloseDesign.setToolTipText("Close Designs");
    btnSaveDesign.setToolTipText("Save current Design");
    btnSaveAllDesigns.setToolTipText("Save all open Designs");

    btnDelete.setToolTipText("Delete item");
    btnAddArtwork.setToolTipText("Add Artwork");
    btnAddBoard.setToolTipText("Add Board");
    btnInsertBoard.setToolTipText(
        "Insert a new Board to inside of selected item");
    btnInsertFoil.setToolTipText("Insert a new Foil to inside of selected item");
    btnInsertSlip.setToolTipText("Insert a new Slip to inside of selected item");
    btnAddGlass.setToolTipText("Add Glass");
    btnInsertFrame.setToolTipText(
        "Insert a new Frame to inside of selected item");
    btnAddFrame.setToolTipText("Add Frame");
    btnAddBack.setToolTipText("Add Back");

    btnCreateBox.setToolTipText("Create Box");

    btnPrevDesign.setToolTipText("Move to previous design");
    btnNextDesign.setToolTipText("Move to next design");
    btnFindDesign.setToolTipText("Select open design");

    btnPrint.setToolTipText("Print current design");
    btnAllocateStock.setToolTipText("Allocate stock to the current design");
    btnOffCuts.setToolTipText("Insert off cuts of current design");

    btnNewDesign.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        des.createNewDesign();
      }
    });

    btnOpenDesign.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        openDesigns();
      }
    });

    btnCloseDesign.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        closeDesigns();
      }
    });

    btnSaveDesign.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        des.saveDesign();
      }
    });

    btnSaveAllDesigns.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        des.saveAllDesigns();
      }
    });

    btnDelete.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        des.deleteItem();
      }
    });

    btnAddArtwork.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        des.addItem(Designer.ITEM_ARTWORK);
      }
    });

    btnAddBoard.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        des.addItem(Designer.ITEM_BOARD);
      }
    });

    btnInsertBoard.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        des.insertItem(Designer.ITEM_BOARD);
      }
    });

    btnInsertFoil.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        des.insertItem(Designer.ITEM_FOIL);
      }
    });

    btnInsertSlip.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        des.insertItem(Designer.ITEM_SLIP);
      }
    });

    btnAddGlass.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        des.addItem(Designer.ITEM_GLASS);
      }
    });

    btnInsertFrame.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        des.insertItem(Designer.ITEM_FRAME);
      }
    });

    btnAddFrame.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        des.addItem(Designer.ITEM_FRAME);
      }
    });

    btnAddBack.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        des.addItem(Designer.ITEM_BACK);
      }
    });

    btnCreateBox.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        des.addItem(Designer.ITEM_BOX);
      }
    });

    btnPrevDesign.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        des.previousDesign();
      }
    });

    btnNextDesign.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        des.nextDesign();
      }
    });

    btnFindDesign.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ArrayList designList = new ArrayList();
        Iterator it = des.getDesignList().iterator();
        while (it.hasNext()) {
          DesignDets item = (DesignDets) it.next();
          Object[] rekord = new Object[5];
          rekord[0] = new Boolean(false);
          rekord[1] = new Integer(item.getDesignID());
          rekord[2] = new String(item.getTitle());
          rekord[3] = new Integer(item.getDate());
          rekord[4] = new String(item.getStatus());
          designList.add(rekord);
        }
        OpenDesign openDialog = new OpenDesign(designList, "Select Open Design");
        int index = openDialog.getFirstSelectedIndex();
        if (index > -1) {
          des.setCurrentDesign(index);
        }
      }
    });

    btnPrint.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        des.btnPrint_actionPerformed();
      }
    });

    btnAllocateStock.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        des.extractStock();
      }
    });

    btnOffCuts.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        des.updateCurrentDesignOffCuts();
      }
    });
  }

  // Fokken menu klas
  class Menubar
      extends JMenuBar {
    public Menubar() {
      // File
      JMenu menu = new JMenu("File");
      menu.setMnemonic(KeyEvent.VK_F);

      JMenuItem item = new JMenuItem("New Design", KeyEvent.VK_N);
      item.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          des.createNewDesign();
        }
      });
      menu.add(item);

      item = new JMenuItem("Open Designs", KeyEvent.VK_O);
      item.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          openDesigns();
        }
      });
      menu.add(item);

      item = new JMenuItem("Close Designs", KeyEvent.VK_C);
      item.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          closeDesigns();
        }
      });
      menu.add(item);

      item = new JMenuItem("Delete Designs", KeyEvent.VK_D);
      item.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          ArrayList designLys = db_conn.getDesignLys();
          OpenDesign deleteDialog = new OpenDesign(designLys, "Delete Designs");
          ArrayList deleteList = deleteDialog.getSelectedDesigns();
          des.deleteDesigns(deleteList);
        }
      });
      menu.add(item);

      menu.addSeparator();
      item = new JMenuItem("Export Design", KeyEvent.VK_E);
      item.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          des.exportDesign();
        }
      });
      menu.add(item);


      menu.addSeparator();
      item = new JMenuItem("Save", KeyEvent.VK_S);
      item.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          des.saveDesign();
        }
      });
      menu.add(item);

      item = new JMenuItem("Save All", KeyEvent.VK_A);
      item.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          des.saveAllDesigns();
        }
      });
      menu.add(item);

      menu.addSeparator();
      item = new JMenuItem("Backup", KeyEvent.VK_B);
      item.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          String mes = "Do you want to make a backup of the current database?";
          if (JOptionPane.showConfirmDialog(frame, mes, "Backup", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            File path = new File("backups/");
            String filename = "BU" + Utils.getCurrentDate();
            int count = 1;
            String[] list = path.list();
            for (int i = 0; i < list.length; i++) {
              if (list[i].indexOf(filename) != -1) {
                count++;
              }
            }
            filename = "backups/" + filename + count;
            ProgressDialog dialog = new ProgressDialog("Backup in progress");
            CreateBackup createBackup = new CreateBackup(dialog, filename);
            dialog.setVisible(true);
          }
        }
      });
      menu.add(item);

      item = new JMenuItem("Restore", KeyEvent.VK_R);
      item.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          try {
            if (des.getDesignList().size() > 0) {
              String mes = "All the open designs must be closed before the restore can continue.";
              JOptionPane.showMessageDialog(frame, mes, "Error", JOptionPane.ERROR_MESSAGE);
              return;
            }
          } catch (NullPointerException en) {
            // kan net voort gaan
          }

          //des.closeDesigns(des.getDesignList(), true);
          File path = new File("backups/");
          String[] list = path.list();
          RestoreDialog dialog = new RestoreDialog(list);
          String filename = dialog.getFileSelected();
          if (filename != null) {
            String backupFile = "backups/" + filename;
            int i = filename.indexOf("BU");
            if (i == -1) {
              String mes = "Invalid backup directory.  The data will not be restored.";
              JOptionPane.showMessageDialog(frame, mes, "Error", JOptionPane.ERROR_MESSAGE);
              return;
            }

            String mes = "The current data will be replaced with the selected backup data.  ";
            mes += "Are you sure you want to continue?";
            if (JOptionPane.showConfirmDialog(frame, mes, "Restore", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
              ProgressDialog d = new ProgressDialog("Restore in progress");
              CreateRestore createRestore = new CreateRestore(d, backupFile);
              d.setVisible(true);
            }
          }
        }
      });
      menu.add(item);

      menu.addSeparator();
      item = new JMenuItem("Exit", KeyEvent.VK_X);
      item.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          des.checkModifiedDesigns();
          ArtofDB.getCurrentDB().closeConnection();
          System.exit(0);
        }
      });
      menu.add(item);
      add(menu);

          /*-------------------------------- Designs --------------------------------*/

      menu = new JMenu("Designs");
      menu.setMnemonic(KeyEvent.VK_D);

      JMenu subMenu = new JMenu("Add");
      subMenu.setMnemonic(KeyEvent.VK_A);

      item = new JMenuItem("Add Artwork", KeyEvent.VK_A);
      item.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          des.addItem(Designer.ITEM_ARTWORK);
        }
      });
      subMenu.add(item);

      item = new JMenuItem("Add Board", KeyEvent.VK_B);
      item.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          des.addItem(Designer.ITEM_BOARD);
        }
      });
      subMenu.add(item);

      item = new JMenuItem("Add Frame", KeyEvent.VK_F);
      item.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          des.addItem(Designer.ITEM_FRAME);
        }
      });
      subMenu.add(item);

      item = new JMenuItem("Add Glass", KeyEvent.VK_G);
      item.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          des.addItem(Designer.ITEM_GLASS);
        }
      });
      subMenu.add(item);

      item = new JMenuItem("Add Back", KeyEvent.VK_B);
      item.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          des.addItem(Designer.ITEM_BACK);
        }
      });
      subMenu.add(item);

      item = new JMenuItem("Create Box", KeyEvent.VK_X);
      item.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          des.addItem(Designer.ITEM_BOX);
        }
      });
      subMenu.add(item);

      menu.add(subMenu);

      subMenu = new JMenu("Insert");
      subMenu.setMnemonic(KeyEvent.VK_I);

      item = new JMenuItem("Insert Board", KeyEvent.VK_B);
      item.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          des.insertItem(Designer.ITEM_BOARD);
        }
      });
      subMenu.add(item);

      item = new JMenuItem("Insert Frame", KeyEvent.VK_F);
      item.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          des.insertItem(Designer.ITEM_FRAME);
        }
      });
      subMenu.add(item);

      item = new JMenuItem("Insert Foil", KeyEvent.VK_F);
      item.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          des.insertItem(Designer.ITEM_FOIL);
        }
      });
      subMenu.add(item);

      item = new JMenuItem("Insert Slip", KeyEvent.VK_S);
      item.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          des.insertItem(Designer.ITEM_SLIP);
        }
      });
      subMenu.add(item);

      menu.add(subMenu);

      item = new JMenuItem("Delete Item", KeyEvent.VK_D);
      item.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          des.deleteItem();
        }
      });
      menu.add(item);

      menu.addSeparator();
      item = new JMenuItem("Refresh Method Preferences", KeyEvent.VK_M);
      item.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          des.refreshMethodPreferences();
        }
      });
      menu.add(item);

      item = new JMenuItem("Refresh Business Preferences", KeyEvent.VK_B);
      item.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          des.refreshBusinessPreferences();
        }
      });
      menu.add(item);

      item = new JMenuItem("Refresh Materials", KeyEvent.VK_M);
      item.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          des.refreshMaterials();
        }
      });
      menu.add(item);

      menu.addSeparator();
      item = new JMenuItem("Next Design", KeyEvent.VK_N);
      item.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          des.nextDesign();
        }
      });
      menu.add(item);

      item = new JMenuItem("Previous Design", KeyEvent.VK_P);
      item.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          des.previousDesign();
        }
      });
      menu.add(item);

      item = new JMenuItem("Print Design", KeyEvent.VK_R);
      item.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          des.btnPrint_actionPerformed();
        }
      });
      menu.add(item);
      add(menu);

          /*----------------------------- Preferences ------------------------------*/

      menu = new JMenu("Preferences");
      menu.setMnemonic(KeyEvent.VK_P);
      item = new JMenuItem("Business Preferences", KeyEvent.VK_B);
      item.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          BusPreferences prefs = new BusPreferences(db_conn);
          lblMUDiscount.setText(String.valueOf(BusPrefDets.getMUDiscSelected() +
                                               1));
        }
      });
      menu.add(item);
      menu.addSeparator();
      item = new JMenuItem("Method Preferences", KeyEvent.VK_M);
      item.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          MethodPreferences prefs = new MethodPreferences(db_conn);
        }
      });
      menu.add(item);
      add(menu);

      // Artists and Clients
      menu = new JMenu("Artists and Clients");
      menu.setMnemonic(KeyEvent.VK_A);
      item = new JMenuItem("Clients", KeyEvent.VK_C);
      item.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          Clients2 clients = new Clients2(db_conn);
        }
      });
      menu.add(item);
      menu.addSeparator();
      item = new JMenuItem("Artists", KeyEvent.VK_A);
      item.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          Artists2 art = new Artists2(db_conn);
        }
      });
      menu.add(item);
      add(menu);

          /*----------------------------- Materials ------------------------------*/

      menu = new JMenu("Materials");
      menu.setMnemonic(KeyEvent.VK_M);
      item = new JMenuItem("Boards", KeyEvent.VK_B);
      item.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          MaterialDialog dialog = new MaterialDialog(MaterialDets.MAT_BOARD);
        }
      });
      menu.add(item);
      item = new JMenuItem("Glass and Backs", KeyEvent.VK_G);
      item.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          MaterialDialog dialog = new MaterialDialog(MaterialDets.MAT_GB);
        }
      });
      menu.add(item);

      menu.addSeparator();
      item = new JMenuItem("Frames", KeyEvent.VK_F);
      item.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          MaterialDialog dialog = new MaterialDialog(MaterialDets.MAT_FRAME);
        }
      });
      menu.add(item);

      menu.addSeparator();
      item = new JMenuItem("Slips", KeyEvent.VK_S);
      item.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          MaterialDialog dialog = new MaterialDialog(MaterialDets.MAT_SLIP);
        }
      });
      menu.add(item);
      item = new JMenuItem("Foils", KeyEvent.VK_O);
      item.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          MaterialDialog dialog = new MaterialDialog(MaterialDets.MAT_FOIL);
        }
      });
      menu.add(item);

      menu.addSeparator();
      item = new JMenuItem("Suppliers", KeyEvent.VK_U);
      item.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          SupplierDialog dialog = new SupplierDialog();
        }
      });
      menu.add(item);

      menu.addSeparator();
      item = new JMenuItem("Import", KeyEvent.VK_I);
      item.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          MaterialClient dialog = new MaterialClient();
        }
      });
      menu.add(item);

      item = new JMenuItem("Remove Materials");
      item.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          RemoveDialog dialog = new RemoveDialog();
        }
      });
      menu.add(item);

      add(menu);

          /*------------------------------- Stock ---------------------------------*/

      menu = new JMenu("Stock");
      menu.setMnemonic(KeyEvent.VK_S);

      item = new JMenuItem("Orders In", KeyEvent.VK_O);
      item.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          artof.stock.orders.OrderInDialog d = new artof.stock.orders.
              OrderInDialog();
        }
      });
      menu.add(item);

      item = new JMenuItem("Offcuts", KeyEvent.VK_C);
      item.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          artof.stock.offcuts.OffCutDialog d = new artof.stock.offcuts.
              OffCutDialog();
        }
      });
      menu.add(item);

      menu.addSeparator();
      item = new JMenuItem("For Designs", KeyEvent.VK_D);
      item.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          artof.stock.designs.DesignDialog d = new artof.stock.designs.
              DesignDialog();
        }
      });
      menu.add(item);

      item = new JMenuItem("Sales and Returns", KeyEvent.VK_S);
      item.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          artof.stock.sales.SaleDialog d = new artof.stock.sales.SaleDialog();
        }
      });
      menu.add(item);

      menu.addSeparator();
      item = new JMenuItem("Stock Take", KeyEvent.VK_T);
      item.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          artof.stock.stocktake.StockTakeDialog d = new artof.stock.stocktake.
              StockTakeDialog();
        }
      });
      menu.add(item);

      item = new JMenuItem("Previous Stock Takes", KeyEvent.VK_P);
      item.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          artof.stock.stocktake.PrevStockDialog d = new artof.stock.stocktake.
              PrevStockDialog();
        }
      });
      menu.add(item);

      item = new JMenuItem("Discrepencies", KeyEvent.VK_I);
      item.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          artof.stock.stocktake.DiffDialog d = new artof.stock.stocktake.
              DiffDialog();
        }
      });
      menu.add(item);

      menu.addSeparator();
      item = new JMenuItem("Current Stock", KeyEvent.VK_U);
      item.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          artof.stock.CurrentStockDialog d = new artof.stock.CurrentStockDialog();
        }
      });
      menu.add(item);
      add(menu);

      /*----------------------------- Settings ------------------------------*/

      menu = new JMenu("Settings");
      menu.setMnemonic(KeyEvent.VK_E);

      item = new JMenuItem("Settings", KeyEvent.VK_S);
      item.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          //des.showEditDesignSettings();
          SettingDialog d = new SettingDialog();
          des.repaint();
        }
      });
      menu.add(item);

      item = new JMenuItem("Owner Details", KeyEvent.VK_O);
      item.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          OwnerDialog d = new OwnerDialog();
        }
      });
      menu.add(item);

      menu.addSeparator();
      subMenu = new JMenu("Registration");
      subMenu.setMnemonic(KeyEvent.VK_R);

      item = new JMenuItem("By Internet", KeyEvent.VK_I);
      item.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          InternetRegDialog d = new InternetRegDialog(frame, "Registration", true);
        }
      });
      subMenu.add(item);

      item = new JMenuItem("By Phone", KeyEvent.VK_P);
      item.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          RegistrationDialog d = new RegistrationDialog(frame, "Registration", true);
        }
      });
      subMenu.add(item);

      menu.add(subMenu);
      add(menu);

          /*------------------------------- About ---------------------------------*/

      menu = new JMenu("Help");
      menu.setMnemonic(KeyEvent.VK_H);

      item = new JMenuItem("Help", KeyEvent.VK_H);
      try {
        File f = new File("help/RSFHelp.hs");
        HelpSet helpSet = new HelpSet(null, f.toURL());
        HelpBroker helpBroker = helpSet.createHelpBroker();
        item.addActionListener(new CSH.DisplayHelpFromSource(helpBroker));

      }
      catch (Exception e) {
        item.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            String mes = "Help information not available";
            JOptionPane.showMessageDialog(frame, mes, "Error",
                                          JOptionPane.ERROR_MESSAGE);
          }
        });
      }
      menu.add(item);

      menu.addSeparator();
      item = new JMenuItem("About", KeyEvent.VK_A);
      item.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          AboutDialog d = new AboutDialog();
        }
      });
      menu.add(item);
      add(menu);

      /*----------------------- Disable as db foutief -------------------------*/

//		if (!stufferCheck || !deletionCheck || !expiryCheck || db_conn == null) {
      if (db_conn == null) {
        for (int i = 0; i < getComponentCount(); i++) {
          try {
            String text = ( (JMenu) getComponent(i)).getText();
            if (!text.equals("Settings") && !text.equals("Help")) {
              getComponent(i).setEnabled(false);

            }
          }
          catch (ClassCastException e) {
            // doen fokkol
          }
        }
      }
    }
  }

  public final void initializeLookAndFeel() {
    UIManager.put("ClassLoader", UIManager.class.getClassLoader());
    try {
      kunststoffLnF = new KunststoffLookAndFeel();
      kunststoffLnF.setCurrentTheme(new KunststoffTheme());
      UIManager.setLookAndFeel(kunststoffLnF);
    }
    catch (javax.swing.UnsupportedLookAndFeelException ex) {
      // handle exception or not, whatever you prefer
    }
    UIManager.getLookAndFeelDefaults().put("ClassLoader",
                                           frame.getClass().getClassLoader());
  }

  private void openDesigns() {
    try {
      ArrayList designLys = db_conn.getDesignLys();
      OpenDesign openDialog = new OpenDesign(designLys, "Open Designs");
      ArrayList openList = openDialog.getSelectedDesigns();
      des.openDesigns(openList);
    }
    catch (NullPointerException e) {
      // doen niks
    }
  }

  private void closeDesigns() {
    ArrayList designLys = des.getDesignList();
    ArrayList codeLys = new ArrayList();
    Iterator it = designLys.iterator();
    while (it.hasNext()) {
      DesignDets design = (DesignDets) it.next();
      Object[] rekord = new Object[5];
      rekord[0] = new Boolean(false);
      rekord[1] = new Integer(design.getDesignID());
      rekord[2] = new String(design.getTitle());
      rekord[3] = new Integer(design.getDate());
      rekord[4] = new String(design.getStatus());
      codeLys.add(rekord);
    }
    OpenDesign closeDialog = new OpenDesign(codeLys, "Close Designs");
    ArrayList closeList = closeDialog.getSelectedDesigns();
    des.closeDesigns(closeList, true);
  }

  class ShowIntro extends Thread {
    private Introduction intro;

    public ShowIntro() {
      intro = new Introduction();
      start();
    }

    public void run() {
      try {
        Thread.sleep(3000);
        while (!startupComplete) {
          Thread.sleep(2000);
        }
        intro.hide();
      }
      catch (InterruptedException e) {

      }
    }
  }

  class CreateBackup extends Thread {
    private ProgressDialog dialog;
    private String backupDir;
    private String srcDir = "";

    public CreateBackup(ProgressDialog dialog, String backupDir) {
      this.dialog = dialog;
      this.backupDir = backupDir;

      dialog.startBulshitting();

      // gee net dialog kans om op te kom en te begin bullshit
      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
        // fok voort
      }
      start();
    }

    public void run() {
      try {
        File f = new File(backupDir);
        if (!f.mkdir())
          throw new Exception();

        f = new File(backupDir + "/materials");
        if (!f.mkdir())
          throw new Exception();

        Archiver archiver = new Archiver();
        File path = new File("rsfdb/data/");
        String[] list = path.list();
        for (int i = 0; i < list.length; i++) {
          if ("Settings.dat".equals(list[i]))
            continue;

          archiver.copy("rsfdb/data/" + list[i], backupDir + "/" + list[i]);
        }

        // material images
        path = new File("images/materials/");
        list = path.list();
        for (int i = 0; i < list.length; i++) {
          archiver.copy("images/materials/" + list[i], backupDir + "/materials/" + list[i]);
        }

        dialog.stopBulshitting();
        String mes = "Backup completed.";
        JOptionPane.showMessageDialog(frame, mes, "Backup", JOptionPane.INFORMATION_MESSAGE);

      } catch (Exception x) {
        dialog.stopBulshitting();
        String mes = "An error occured.  The data will not be backed up.";
        JOptionPane.showMessageDialog(frame, mes, "Error", JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  class CreateRestore extends Thread {
    private ProgressDialog dialog;
    private String dirToRestore;

    public CreateRestore(ProgressDialog dialog, String dirToRestore) {
      this.dialog = dialog;
      this.dirToRestore = dirToRestore;
      dialog.startBulshitting();

      // gee net dialog kans om op te kom en te begin bullshit
      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
        // fok voort
      }
      start();
    }

    public void run() {
      try {
        ArtofDB.getCurrentDB().closeConnection();
        File path = new File("rsfdb/data/");
        String[] list = path.list();
        for (int i = 0; i < list.length; i++) {
          if ("Settings.dat".equals(list[i]))
            continue;

          File f = new File("rsfdb/data/" + list[i]);
          f.delete();
        }

        Archiver archiver = new Archiver();
        path = new File(dirToRestore);
        list = path.list();
        for (int i = 0; i < list.length; i++) {
          if ("materials".equals(list[i]))
            continue;

          archiver.copy(dirToRestore + "/" + list[i], "rsfdb/data/" + list[i]);
        }

        try {
          db_conn = ArtofDB.getCurrentDB();
          DesignBoard.rebuildBoardMapper();
          DesignFrame.rebuildFrameMapper();
          DesignSlip.rebuildSlipMapper();
          DesignFoil.rebuildFoilMapper();
          DesignGlassAndBack.rebuildGBMapper();

          TypeMapper.buildTypeMapper();

        } catch (NullPointerException e) {
          e.printStackTrace();
        }

        // restore material images
        path = new File(dirToRestore + "/materials/");
        list = path.list();
        for (int i = 0; i < list.length; i++) {
          File f = new File("images/materials/" + list[i]);
          f.delete();
          archiver.copy(dirToRestore + "/materials/" + list[i], "images/materials/" + list[i]);
        }

        dialog.stopBulshitting();
        String mes = "Restore completed.";
        JOptionPane.showMessageDialog(frame, mes, "Restore", JOptionPane.INFORMATION_MESSAGE);

      } catch (Exception x) {
        x.printStackTrace();
        dialog.hide();
        String mes = "An error occured.  The database cannot not be restored.";
        JOptionPane.showMessageDialog(frame, mes, "Error", JOptionPane.ERROR_MESSAGE);
      }
    }

  }
}
