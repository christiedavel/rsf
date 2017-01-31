package artof.database;

import artof.designer.Designer;
import artof.designitems.DesignItem2;
import artof.utils.UserSettings;
import artof.utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.print.*;
import java.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class DesignDets
			implements Comparable, Comparator, Printable {
	//static final long serialVersionUID = 7024816005682407777L;
	private int designID = -1;
	private String title = "New Design";
	private int artistID = -1;
	private int clientID = -1;
	private String status;
	private int desDate;
	private float materialAdj = 0;
	private float labourAdj = 0;
	private boolean useWeighting = true;
	private boolean useStretching = false;
	private boolean usePasting = false;

	private int deliveryDate = Utils.getCurrentDate();
	private float priceOne = 0;
	private float discountOne = 0;
	private int noOrdered = 1;
	private float priceOther = 0;
	private float discountOther = 0;

	private BusPrefDets busPrefs;
	private MethodPrefDets methodPrefs;
	private LinkedList itemList = new LinkedList();
	private boolean modified = false;

	private static TreeMap artistNaamIDMap = null;
	private static TreeMap artistIDNaamMap = null;
	private static TreeMap clientNaamIDMap = null;
	private static TreeMap clientIDNaamMap = null;
	private JScrollPane jScrollPane = null;
	ClientDets client = null;

	//vir die faktuur printer
	ArrayList designList = new ArrayList();

	public DesignDets() {
	}

	public DesignDets(BusPrefDets busPrefs, MethodPrefDets methodPrefs,
										String title, ArtofDB db) throws NullPointerException {
		if (busPrefs == null || methodPrefs == null || title == null) {
			throw new NullPointerException();
		}
		else {
			this.busPrefs = busPrefs;
			this.methodPrefs = methodPrefs;
			this.title = title;
			if (artistNaamIDMap == null) {
				updateArtistMaps(db);
			}
			if (clientNaamIDMap == null) {
				updateArtistMaps(db);
			}
		}
	}

	public void setDesignList(ArrayList designList) {
		this.designList = designList;
	}

	public static void updateArtistMaps(ArtofDB db) {
		try {
			artistNaamIDMap = db.getArtists();
			artistIDNaamMap = new TreeMap();
			Iterator it = artistNaamIDMap.keySet().iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				artistIDNaamMap.put(artistNaamIDMap.get(key), key);
			}
		}
		catch (NullPointerException e) {
			// doen niks
		}
	}

	public static void updateClientMaps(ArtofDB db) {
		try {
			clientNaamIDMap = db.getClients();
			clientIDNaamMap = new TreeMap();
			Iterator it = clientNaamIDMap.keySet().iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				clientIDNaamMap.put(clientNaamIDMap.get(key), key);
			}
		}
		catch (NullPointerException e) {
			// doen niks
		}
	}

	public boolean isModified() {
		if (modified || busPrefs.isModified() || methodPrefs.isModified()) {
			return true;
		}
		else {
			return false;
		}
	}

	public void setModified(boolean mod) {
		modified = mod;
	}

	public int compareTo(Object obj) throws ClassCastException {
		Integer curID = new Integer(designID);
		Integer objID = new Integer( ( (DesignDets) obj).getDesignID());
		return curID.compareTo(objID);
	}

	public int compare(Object o1, Object o2) throws ClassCastException {
		return ( (DesignDets) o1).compareTo(o2);
	}

	public boolean equals(Object obj) {
		try {
			if ( ( (DesignDets) obj).getDesignID() == designID) {
				return true;
			}
			else {
				return false;
			}
		}
		catch (ClassCastException e) {
			return false;
		}
	}

	public int getDesignID() {
		return designID;
	}

	public void setDesignID(int id) {
		if (designID != id) {
			designID = id;
			setModified(true);
		}
	}

	public String getTitle() {
		if (title == null || title.equals("null")) {
			return "";
		}
		else {
			return title;
		}
	}

	public void setTitle(String t) {
		if ( (title != null && !title.equals(t)) || (title == null && t != null)) {
			title = t;
			setModified(true);
		}
	}

	public int getArtistID() {
		return artistID;
	}

	public void setArtistID(int id) {
		if (artistID != id) {
			artistID = id;
			setModified(true);
		}
	}

	public String getArtist() {
		try {
			return (String) artistIDNaamMap.get(new Integer(artistID));
		}
		catch (NullPointerException e) {
			return "";
		}
		catch (IndexOutOfBoundsException e) {
			return "";
		}
	}

	public void setArtist(String artist) {
		try {
			artistID = ( (Integer) artistNaamIDMap.get(artist)).intValue();
			int j = 0;
		}
		catch (NullPointerException e) {
			artistID = -1;
		}
		catch (IndexOutOfBoundsException e) {
			artistID = -1;
		}
	}

	public static Iterator getArtistIterator() throws NullPointerException {
		return artistNaamIDMap.keySet().iterator();
	}

	public int getClientID() {
		return clientID;
	}

	public void setClientID(int id) {
		if (clientID != id) {
			clientID = id;
			setModified(true);
		}
	}

	public String getClient() {
		try {
			return (String) clientIDNaamMap.get(new Integer(clientID));
		}
		catch (NullPointerException e) {
			return "";
		}
		catch (IndexOutOfBoundsException e) {
			return "";
		}
	}

	public void setClient(String client) {
		try {
			clientID = ( (Integer) clientNaamIDMap.get(client)).intValue();
		}
		catch (NullPointerException e) {
			clientID = -1;
		}
		catch (IndexOutOfBoundsException e) {
			clientID = -1;
		}
	}

	public static Iterator getClientIterator() throws NullPointerException {
		return clientNaamIDMap.keySet().iterator();
	}

	public static int getClientIDFromName(String name) {
		try {
			return ( (Integer) clientNaamIDMap.get(name)).intValue();
		}
		catch (NullPointerException e) {
			return -1;
		}
	}

	public String getStatus() {
		if (status == null || status.equals("null")) {
			return "";
		}
		else {
			return status;
		}
	}

	public void setStatus(String t) {
		if ( (status != null && !status.equals(t)) || (status == null && t != null)) {
			status = t;
			setModified(true);
		}
	}

	public int getDate() {
		return desDate;
	}

	public void setDate(int d) {
		if (desDate != d) {
			desDate = d;
			setModified(true);
		}
	}

	public float getMaterialAdj() {
		return materialAdj;
	}

	public void setMaterialAdj(float d) {
		if (materialAdj != d) {
			materialAdj = d;
			setModified(true);
		}
	}

	public float getLabourAdj() {
		return labourAdj;
	}

	public void setLabourAdj(float d) {
		if (labourAdj != d) {
			labourAdj = d;
			setModified(true);
		}
	}

	public boolean getUseWeighting() {
		return useWeighting;
	}

	public void setUseWeighting(boolean d) {
		if (useWeighting != d) {
			useWeighting = d;
			setModified(true);
		}
	}

	public int getUseWeightingInt() {
		if (getUseWeighting()) {
			return 1;
		}
		else {
			return 0;
		}
	}

	public void setUseWeighting(int d) {
		if (d == 1) {
			setUseWeighting(true);
		}
		else {
			setUseWeighting(false);
		}
	}

	public boolean getUseStretching() {
		return useStretching;
	}

	public void setUseStretching(boolean d) {
		if (useStretching != d) {
			useStretching = d;
			setModified(true);
		}
	}

	public int getUseStretchingInt() {
		if (getUseStretching()) {
			return 1;
		}
		else {
			return 0;
		}
	}

	public void setUseStretching(int d) {
		if (d == 1) {
			setUseStretching(true);
		}
		else {
			setUseStretching(false);
		}
	}

	public boolean getUsePasting() {
		return usePasting;
	}

	public void setUsePasting(boolean d) {
		if (usePasting != d) {
			usePasting = d;
			setModified(true);
		}
	}

	public int getUsePastingInt() {
		if (getUsePasting()) {
			return 1;
		}
		else {
			return 0;
		}
	}

	public void setUsePasting(int d) {
		if (d == 1) {
			setUsePasting(true);
		}
		else {
			setUsePasting(false);
		}
	}

	public int getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(int d) {
		if (deliveryDate != d) {
			deliveryDate = d;
			setModified(true);
		}
	}

	public float getPriceOne() {
		return priceOne;
	}

	public void setPriceOne(float d) {
		if (priceOne != d) {
			priceOne = d;
			setModified(true);
		}
	}

	public float getDiscountOne() {
		return discountOne;
	}

	public void setDiscountOne(float d) {
		if (discountOne != d) {
			discountOne = d;
			setModified(true);
		}
	}

	public int getNoOrdered() {
		return noOrdered;
	}

	public void setNoOrdered(int d) {
		if (noOrdered != d) {
			noOrdered = d;
			setModified(true);
		}
	}

	public float getPriceOther() {
		return priceOther;
	}

	public void setPriceOther(float d) {
		if (priceOther != d) {
			priceOther = d;
			setModified(true);
		}
	}

	public float getDiscountOther() {
		return discountOther;
	}

	public void setDiscountOther(float d) {
		if (discountOther != d) {
			discountOther = d;
			setModified(true);
		}
	}

	public BusPrefDets getBusPrefs() {
		return busPrefs;
	}

	public void setBusPrefs(BusPrefDets det) throws NullPointerException {
		if (det == null) {
			throw new NullPointerException();
		}
		else {
			busPrefs = det;
			setModified(true);
		}
	}

	public MethodPrefDets getMethodPrefs() {
		return methodPrefs;
	}

	public void setMethodPrefs(MethodPrefDets det) throws NullPointerException {
		if (det == null) {
			throw new NullPointerException();
		}
		else {
			methodPrefs = det;
			setModified(true);
		}
	}

	public LinkedList getItemList() {
		return itemList;
	}

	public void setItemList(LinkedList list) throws NullPointerException {
		if (list == null) {
			throw new NullPointerException();
		}
		else {
			itemList = list;
			setModified(true);
		}
	}

	/******************************* Print kak ************************************/

	public boolean printDesignQuotation() {
		PrinterJob printerJob = PrinterJob.getPrinterJob();
		Book book = new Book();
		book.append(this, new PageFormat(), 1);
		printerJob.setPageable(book);
		boolean doPrint = printerJob.printDialog();
		if (doPrint) {
			try {
				printerJob.print();
			}
			catch (PrinterException exception) {
				JOptionPane.showMessageDialog(jScrollPane,
																			"Printer error.  The clients details cannot be printed.", "Error",
																			JOptionPane.ERROR_MESSAGE);
			}
		}
		return doPrint;
	}

	private double addStringGap(Graphics2D g2d, FontRenderContext frc, String s,
															double top, double left, double width,
															boolean alignRight) {
		try {
			Font font = new Font("Times New Roman", Font.PLAIN, 9);
			TextLayout layout = new TextLayout(s, font, frc);
			Rectangle2D bounds = layout.getBounds();
			double w = bounds.getWidth();
			if (alignRight) {
				layout.draw(g2d, (float) (left + width - w), (float) top);
			}
			else {
				layout.draw(g2d, (float) left, (float) top);
			}
		}
		catch (IllegalArgumentException e) {
		}
		return left + width;
	}

	public int print(Graphics g, PageFormat format, int pageIndex) {
		if (status.equals("In Order")) {
			printSnyLys(g, format, pageIndex);
			//PrintFrame pp = new PrintFrame(g, format, pageIndex);
		}
		else if (status.equals("Quotation")) {
			printQuotation(g, format, pageIndex);
			//PrintFrame pp = new PrintFrame(g, format, pageIndex);
		}
		else if (status.equals("Completed")) {
			printFaktuur(g, format, pageIndex);
			//PrintFrame pp = new PrintFrame(g, format, pageIndex);
		}

		return Printable.PAGE_EXISTS;
	}

	private void printQuotation(Graphics g, PageFormat format, int pageIndex) {
		Graphics2D g2d = (Graphics2D) g;

		g2d.translate(format.getImageableX(), format.getImageableY());
		g2d.setPaint(Color.black);
		double pageWidth = format.getImageableWidth();
		double pageHeight = format.getImageableHeight();
		double curY = 0;

		g2d.setFont(g2d.getFont().deriveFont(Font.BOLD + Font.ITALIC, 30));
		FontRenderContext frc = g2d.getFontRenderContext();
		try {
			TextLayout layout =
						new TextLayout(UserSettings.ownerCompany, g2d.getFont(), frc);
			Rectangle2D bounds = layout.getBounds();
			curY += bounds.getHeight();
			layout.draw(
						g2d,
						(float) (pageWidth / 2 - bounds.getWidth() / 2),
						(float) curY);
			curY += bounds.getHeight() * 1.5;
		}
		catch (IllegalArgumentException e) {
		}

		g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 9));
		try {
			String text = "";
			if (UserSettings.ownerTel != null
						&& !UserSettings.ownerTel.equals("")) {
				text += "Tel: " + UserSettings.ownerTel;
			}
			if (UserSettings.ownerFax != null
						&& !UserSettings.ownerFax.equals("")) {
				text += "  " + "Fax: " + UserSettings.ownerFax;
			}
			if (UserSettings.ownerCel != null
						&& !UserSettings.ownerCel.equals("")) {
				text += "  " + "Cel: " + UserSettings.ownerCel;
			}
			if (UserSettings.ownerEmail != null
						&& !UserSettings.ownerEmail.equals("")) {
				text += "  " + "E-mail: " + UserSettings.ownerEmail;
			}
			TextLayout layout = new TextLayout(text, g2d.getFont(), frc);
			Rectangle2D bounds = layout.getBounds();
			curY += bounds.getHeight();
			layout.draw(
						g2d,
						(float) (pageWidth / 2 - bounds.getWidth() / 2),
						(float) curY - 25);
			curY += bounds.getHeight();
		}
		catch (IllegalArgumentException e) {
		}

		try {
			String text = "";
			if (UserSettings.ownerAdd1 != null
						&& !UserSettings.ownerAdd1.equals("")) {
				text += UserSettings.ownerAdd1;
			}
			if (UserSettings.ownerAdd2 != null
						&& !UserSettings.ownerAdd2.equals("")) {
				text += "  " + UserSettings.ownerAdd2;
			}
			if (UserSettings.ownerAdd3 != null
						&& !UserSettings.ownerAdd3.equals("")) {
				text += "  " + UserSettings.ownerAdd3;
			}
			if (UserSettings.ownerCode != null
						&& !UserSettings.ownerCode.equals("")) {
				text += "  " + UserSettings.ownerCode;
			}
			TextLayout layout = new TextLayout(text, g2d.getFont(), frc);
			Rectangle2D bounds = layout.getBounds();
			curY += bounds.getHeight();
			layout.draw(
						g2d,
						(float) (pageWidth / 2 - bounds.getWidth() / 2),
						(float) curY - 25);
			curY += bounds.getHeight();

		}
		catch (IllegalArgumentException e) {
		}

		try {
			curY -= 25;
			Line2D.Double l2d = new Line2D.Double(0, curY, pageWidth, curY);
			g2d = (Graphics2D) g;
			g2d.draw(l2d);

			curY += 25;
			g2d.setFont(g2d.getFont().deriveFont(Font.ITALIC, 20));
			String text = "Quotation";

			TextLayout layout = new TextLayout(text, g2d.getFont(), frc);

			Rectangle2D bounds = layout.getBounds();
			//curY += bounds.getHeight();
			layout.draw(g2d,
									(float) ( (pageWidth / 2) -
														(layout.getBounds().getWidth() / 2)),
									(float) curY);

			curY += bounds.getHeight();
		}
		catch (IllegalArgumentException e) {
		}

		try {

			curY += 35;
			g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 10));
			String text = "Client Details:";

			TextLayout layout = new TextLayout(text, g2d.getFont(), frc);

			Rectangle2D bounds = layout.getBounds();
			//curY += bounds.getHeight();
			layout.draw(g2d, (float) (0), (float) curY);

			text = "Design Details";
			layout = new TextLayout(text, g2d.getFont(), frc);
			bounds = layout.getBounds();
			//curY += bounds.getHeight();
			layout.draw(g2d, (float) pageWidth / 2 - 25, (float) curY);

			curY += bounds.getHeight();
		}
		catch (IllegalArgumentException e) {
		}

		g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 10));
		curY *= 1.1;
		TextLayout layout =
					new TextLayout("0123456789012345", g2d.getFont(), frc);
		double indent = layout.getBounds().getWidth() * 0.9;
		double rindent = indent + 10;
		double height;

		//Christie reel een
		ArtofDB db = ArtofDB.getCurrentDB();
		ClientDets client = db.getClient(getClientID());
		//links veld
		float cIndent = Float.valueOf(String.valueOf(indent)).floatValue() / 5;
		layout = new TextLayout("Name:", g2d.getFont(), frc);
		height = layout.getBounds().getHeight();
		layout.draw(g2d, cIndent, (float) curY);

		try {
			//links waarde
			g2d.setFont(g2d.getFont().deriveFont(Font.ITALIC, 10));
			try {
				layout =
							new TextLayout(
										client.getName() + " " + client.getSurname(),
										g2d.getFont(),
										frc);
				layout.draw(g2d, (float) indent, (float) curY);
			}
			catch (NullPointerException ex) {
				//doen niks
			}

			//regs Veld
			g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 10));
			layout = new TextLayout("Design Code:", g2d.getFont(), frc);
			height = layout.getBounds().getHeight();
			layout.draw(
						g2d,
						(float) ( (pageWidth / 2) + cIndent - 25),
						(float) curY);
			//regs waarde
			g2d.setFont(g2d.getFont().deriveFont(Font.ITALIC, 10));
			layout =
						new TextLayout(String.valueOf(designID), g2d.getFont(), frc);
			layout.draw(
						g2d,
						(float) (pageWidth / 2) + (float) rindent,
						(float) curY);
		}
		catch (IllegalArgumentException e) {
		}

		// Christie reel twee
		try {
			//links veld
			curY += height * 1.5;
			cIndent = Float.valueOf(String.valueOf(indent)).floatValue() / 5;
			g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 10));
			layout = new TextLayout("Tel(Home):", g2d.getFont(), frc);
			height = layout.getBounds().getHeight();
			layout.draw(g2d, cIndent, (float) curY);



			//links waarde
			g2d.setFont(g2d.getFont().deriveFont(Font.ITALIC, 10));
			layout = new TextLayout(client.getHomeTel(), g2d.getFont(), frc);
			layout.draw(g2d, (float) indent, (float) curY);
		}
		catch (IllegalArgumentException e) {
		}
		catch (NullPointerException ex) {
			//doen niks
		}

		try {
			//regs Veld
			g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 10));
			layout = new TextLayout("Design Title:", g2d.getFont(), frc);
			height = layout.getBounds().getHeight();
			layout.draw(
						g2d,
						(float) ( (pageWidth / 2) + cIndent - 25),
						(float) curY);

			//regs waarde
			g2d.setFont(g2d.getFont().deriveFont(Font.ITALIC, 10));
			layout = new TextLayout(String.valueOf(title), g2d.getFont(), frc);
			layout.draw(
						g2d,
						(float) (pageWidth / 2) + (float) rindent,
						(float) curY);
		}
		catch (IllegalArgumentException e) {
		}
		catch (NullPointerException ex) {
			//doen niks
		}

		//              Christie reel drie

		try {
			//links veld
			curY += height * 1.5;
			cIndent = Float.valueOf(String.valueOf(indent)).floatValue() / 5;
			g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 10));
			layout = new TextLayout("Tel(Work):", g2d.getFont(), frc);
			height = layout.getBounds().getHeight();
			layout.draw(g2d, cIndent, (float) curY);

		}
		catch (NullPointerException ex) {
			//doen niks
		}

		try {
			//links waarde
			g2d.setFont(g2d.getFont().deriveFont(Font.ITALIC, 10));
			layout = new TextLayout(client.getWorkTel(), g2d.getFont(), frc);
			layout.draw(g2d, (float) indent, (float) curY);
		}
		catch (IllegalArgumentException e) {
		}
		catch (NullPointerException ex) {
			//doen niks
		}
		try {
			//regs Veld
			g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 10));
			layout = new TextLayout("Design Date:", g2d.getFont(), frc);
			height = layout.getBounds().getHeight();
			layout.draw(
						g2d,
						(float) ( (pageWidth / 2) + cIndent - 25),
						(float) curY);
			//regs waarde
			g2d.setFont(g2d.getFont().deriveFont(Font.ITALIC, 10));
			layout =
						new TextLayout(
									String.valueOf(Utils.getDatumStr(desDate)),
									g2d.getFont(),
									frc);
			layout.draw(
						g2d,
						(float) (pageWidth / 2) + (float) rindent,
						(float) curY);
		}
		catch (IllegalArgumentException e) {
		}
		catch (NullPointerException ex) {
			//doen niks
		}

		//              Christie reel vier

		try {
			//links veld
			curY += height * 1.5;
			cIndent = Float.valueOf(String.valueOf(indent)).floatValue() / 5;
			g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 10));
			layout = new TextLayout("Mobile:", g2d.getFont(), frc);
			height = layout.getBounds().getHeight();
			layout.draw(g2d, cIndent, (float) curY);

			//links waarde
			g2d.setFont(g2d.getFont().deriveFont(Font.ITALIC, 10));
			if (client.getPostTo() == 0) {
				layout =
							new TextLayout(client.getHomeCell(), g2d.getFont(), frc);
			}
			else {
				layout =
							new TextLayout(client.getWorkCell(), g2d.getFont(), frc);
			}
			layout.draw(g2d, (float) indent, (float) curY);
		}
		catch (IllegalArgumentException e) {
		}
		catch (NullPointerException ex) {
			//doen niks
		}
		try {

			//					regs Veld
			g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 10));
			layout = new TextLayout("Price:", g2d.getFont(), frc);
			height = layout.getBounds().getHeight();
			layout.draw(
						g2d,
						(float) ( (pageWidth / 2) + cIndent - 25),
						(float) curY);
			//regs waarde
			g2d.setFont(g2d.getFont().deriveFont(Font.ITALIC, 10));
			if (this.busPrefs.isVATReg() == true) {
				layout =
							new TextLayout(
										"R"
										+ priceOne
										+ " excl"
										+ busPrefs.getVATPerc()
										+ "% VAT",
										g2d.getFont(),
										frc);
			}
			else {
				layout =
							new TextLayout(
										"R"
										+ priceOne,
										g2d.getFont(),
										frc);

			}
			layout.draw(
						g2d,
						(float) (pageWidth / 2) + (float) rindent,
						(float) curY);
		}
		catch (IllegalArgumentException e) {
		}
		catch (NullPointerException ex) {
			//doen niks
		}

		//              Christie reel vyf

		try {
			//links veld
			curY += height * 1.5;
			cIndent = Float.valueOf(String.valueOf(indent)).floatValue() / 5;
			g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 10));
			layout = new TextLayout("Fax:", g2d.getFont(), frc);
			height = layout.getBounds().getHeight();
			layout.draw(g2d, cIndent, (float) curY);

			//links waarde
			g2d.setFont(g2d.getFont().deriveFont(Font.ITALIC, 10));
			if (client.getPostTo() == 0) {
				layout =
							new TextLayout(client.getHomeFax(), g2d.getFont(), frc);
			}
			else {
				layout =
							new TextLayout(client.getWorkFax(), g2d.getFont(), frc);
			}
			layout.draw(g2d, (float) indent, (float) curY);
		}
		catch (IllegalArgumentException e) {
		}
		catch (NullPointerException ex) {
			//doen niks
		}
		try {

			//regs Veld
			g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 10));
			layout = new TextLayout("Discount:", g2d.getFont(), frc);
			height = layout.getBounds().getHeight();
			layout.draw(
						g2d,
						(float) ( (pageWidth / 2) + cIndent - 25),
						(float) curY);
			//regs waarde
			g2d.setFont(g2d.getFont().deriveFont(Font.ITALIC, 10));
			String text = Utils.getCurrencyFormat(discountOne) + " %";
			layout = new TextLayout(text, g2d.getFont(), frc);
			layout.draw(
						g2d,
						(float) (pageWidth / 2) + (float) rindent,
						(float) curY);

		}
		catch (IllegalArgumentException e) {
		}
		catch (NullPointerException ex) {
			//doen niks
		}

		//              Christie reel ses
		try {
			//links veld
			curY += height * 1.5;
			cIndent = Float.valueOf(String.valueOf(indent)).floatValue() / 5;
			g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 10));
			layout = new TextLayout("Address:", g2d.getFont(), frc);
			height = layout.getBounds().getHeight();
			layout.draw(g2d, cIndent, (float) curY);

			//links waarde
			g2d.setFont(g2d.getFont().deriveFont(Font.ITALIC, 10));
			if (client.getPostTo() == 0) {
				layout =
							new TextLayout(client.getHomeAdd1(), g2d.getFont(), frc);
			}
			else {
				layout =
							new TextLayout(client.getWorkAdd1(), g2d.getFont(), frc);
			}
			layout.draw(g2d, (float) indent, (float) curY);
		}
		catch (IllegalArgumentException e) {
		}
		catch (NullPointerException ex) {
			//doen niks
		}

		//regs veld
		try {
			float totalOne = 0;
			if (busPrefs.getVATReg().equals("Yes")) {
				totalOne =
							getPriceOne()
							* (1 + busPrefs.getVATPerc() / 100)
							* (100 - getDiscountOne())
							/ 100;
			}
			else {
				totalOne =
							getPriceOne()
							* (100 - getDiscountOne())
							/ 100;
			}
			//curY += height * 1.5;
			g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 10));
			String text;
			if (busPrefs.isVATReg() == true) {
				text = "Price(Incl 14% VAT): ";
			}
			else {
				text = "Price: ";
			}
			layout = new TextLayout(text, g2d.getFont(), frc);
			height = layout.getBounds().getHeight();
			layout.draw(
						g2d,
						(float) ( (pageWidth / 2) + cIndent - 25),
						(float) curY);
			g2d.setFont(g2d.getFont().deriveFont(Font.ITALIC, 10));
			text = "R" + Utils.getCurrencyFormat(totalOne);
			layout = new TextLayout(text, g2d.getFont(), frc);
			layout.draw(
						g2d,
						(float) (pageWidth / 2) + (float) rindent,
						(float) curY);

		}
		catch (IllegalArgumentException e) {
		}
		catch (NullPointerException ex) {
			//doen niks
		}

		//              Christie reel sewe

		try {

			//links veld
			curY += height * 1.5;
			cIndent = Float.valueOf(String.valueOf(indent)).floatValue() / 5;
			g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 10));
			g2d.setColor(Color.WHITE);
			layout = new TextLayout("asa0 ", g2d.getFont(), frc);
			height = layout.getBounds().getHeight();
			layout.draw(g2d, cIndent, (float) curY);
			g2d.setColor(Color.BLACK);

			//links waarde
			g2d.setFont(g2d.getFont().deriveFont(Font.ITALIC, 10));
			if (client.getPostTo() == 0) {
				layout =
							new TextLayout(client.getHomeAdd2(), g2d.getFont(), frc);
			}
			else {
				layout =
							new TextLayout(client.getWorkAdd2(), g2d.getFont(), frc);
			}
			layout.draw(g2d, (float) indent, (float) curY);

		}
		catch (IllegalArgumentException e) {
		}
		catch (NullPointerException ex) {
			//doen niks
		}

		//regs waarde
		try {
			float totalOne = 0;
			if (busPrefs.getVATReg().equals("Yes")) {
				totalOne =
							getPriceOne()
							* (1 + busPrefs.getVATPerc() / 100)
							* (100 - getDiscountOne())
							/ 100;
			}
			else {
				totalOne =
							getPriceOne()
							* (100 - getDiscountOne())
							/ 100;
			}
			//curY += height * 1.5;
			g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 10));
			String text = "Price for " + noOrdered + " items:";
			layout = new TextLayout(text, g2d.getFont(), frc);
			height = layout.getBounds().getHeight();
			layout.draw(
						g2d,
						(float) ( (pageWidth / 2) + cIndent - 25),
						(float) curY);
			g2d.setFont(g2d.getFont().deriveFont(Font.ITALIC, 10));
			text = "R" + Utils.getCurrencyFormat(totalOne * noOrdered);
			layout = new TextLayout(text, g2d.getFont(), frc);
			layout.draw(
						g2d,
						(float) (pageWidth / 2) + (float) rindent,
						(float) curY);

		}
		catch (IllegalArgumentException e) {
		}
		catch (NullPointerException ex) {
			//doen niks
		}
		//              Christie reel agt

		try {

			//links veld
			curY += height * 1.5;
			cIndent = Float.valueOf(String.valueOf(indent)).floatValue() / 5;
			g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 10));
			g2d.setColor(Color.WHITE);
			layout = new TextLayout("asa1 ", g2d.getFont(), frc);

			height = layout.getBounds().getHeight();
			layout.draw(g2d, cIndent, (float) curY);
			g2d.setColor(Color.BLACK);

			//links waarde
			g2d.setFont(g2d.getFont().deriveFont(Font.ITALIC, 10));
			if (client.getPostTo() == 0) {
				layout =
							new TextLayout(client.getHomeAdd3(), g2d.getFont(), frc);
			}
			else {
				layout =
							new TextLayout(client.getWorkAdd3(), g2d.getFont(), frc);
			}

			layout.draw(g2d, (float) indent, (float) curY);
		}
		catch (IllegalArgumentException e) {
		}
		catch (NullPointerException ex) {
			//doen niks
		}

//regs veld
		try {
			g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 10));
			String text = "Other items:";
			layout = new TextLayout(text, g2d.getFont(), frc);
			height = layout.getBounds().getHeight();
			layout.draw(
						g2d,
						(float) ( (pageWidth / 2) + cIndent - 25),
						(float) curY);
		}
		catch (IllegalArgumentException e) {
		}
		catch (NullPointerException ex) {
			//doen niks
		}
		try {
			//regs waarde
			g2d.setFont(g2d.getFont().deriveFont(Font.ITALIC, 10));
			String text = "R" + Utils.getCurrencyFormat(this.priceOther);
			layout = new TextLayout(text, g2d.getFont(), frc);
			layout.draw(
						g2d,
						(float) (pageWidth / 2) + (float) rindent,
						(float) curY);

		}
		catch (IllegalArgumentException e) {
		}
		catch (NullPointerException ex) {
			//doen niks
		}

		//              Christie reel nege

		try {
			//links veld
			curY += height * 1.5;
			cIndent = Float.valueOf(String.valueOf(indent)).floatValue() / 5;
			g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 10));
			g2d.setColor(Color.WHITE);
			layout = new TextLayout("asa2", g2d.getFont(), frc);
			height = layout.getBounds().getHeight();
			layout.draw(g2d, cIndent, (float) curY);
			g2d.setColor(Color.BLACK);

			//links waarde
			g2d.setFont(g2d.getFont().deriveFont(Font.ITALIC, 10));
			if (client.getPostTo() == 0) {
				layout =
							new TextLayout(client.getHomeAdd4(), g2d.getFont(), frc);
			}
			else {
				layout =
							new TextLayout(client.getWorkAdd4(), g2d.getFont(), frc);
			}

			layout.draw(g2d, (float) indent, (float) curY);

		}
		catch (IllegalArgumentException e) {
		}
		catch (NullPointerException ex) {
			//doen niks
		}
		//regs veld
		try {
			g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 10));
			String text = "Discount:";
			layout = new TextLayout(text, g2d.getFont(), frc);
			height = layout.getBounds().getHeight();
			layout.draw(
						g2d,
						(float) ( (pageWidth / 2) + cIndent - 15),
						(float) curY);
		}
		catch (IllegalArgumentException e) {
		}
		catch (NullPointerException ex) {
			//doen niks
		}
		try {
			//regs waarde
			g2d.setFont(g2d.getFont().deriveFont(Font.ITALIC, 10));
			String text = Utils.getCurrencyFormat(this.discountOther) + " %";
			layout = new TextLayout(text, g2d.getFont(), frc);
			layout.draw(
						g2d,
						(float) (pageWidth / 2) + (float) rindent,
						(float) curY);

		}
		catch (IllegalArgumentException e) {
		}
		catch (NullPointerException ex) {
			//doen niks
		}
		//              Christie reel tien

		try {
			//links veld
			curY += height * 1.5;
			cIndent = Float.valueOf(String.valueOf(indent)).floatValue() / 5;
			g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 10));
			layout = new TextLayout("Code:", g2d.getFont(), frc);
			height = layout.getBounds().getHeight();
			layout.draw(g2d, cIndent, (float) curY);

			//links waarde
			g2d.setFont(g2d.getFont().deriveFont(Font.ITALIC, 10));
			if (client.getPostTo() == 0) {
				layout =
							new TextLayout(client.getHomeCode(), g2d.getFont(), frc);
			}
			else {
				layout =
							new TextLayout(client.getWorkCode(), g2d.getFont(), frc);
			}

			layout.draw(g2d, (float) indent, (float) curY);

		}
		catch (IllegalArgumentException e) {
		}
		catch (NullPointerException ex) {
			//doen niks
		}
//regs veld
		if (busPrefs.isVATReg() == true) {
			try {
				g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 10));
				String text = "VAT:";
				layout = new TextLayout(text, g2d.getFont(), frc);
				height = layout.getBounds().getHeight();
				layout.draw(
							g2d,
							(float) ( (pageWidth / 2) + cIndent - 15),
							(float) curY);
			}
			catch (IllegalArgumentException e) {
			}
			catch (NullPointerException ex) {
				//doen niks
			}
			try {
				//regs waarde
				g2d.setFont(g2d.getFont().deriveFont(Font.ITALIC, 10));
				String text = Utils.getCurrencyFormat(this.getBusPrefs().getVATPerc()) +
							" %";
				layout = new TextLayout(text, g2d.getFont(), frc);
				layout.draw(
							g2d,
							(float) (pageWidth / 2) + (float) rindent,
							(float) curY);

			}
			catch (IllegalArgumentException e) {
			}
			catch (NullPointerException ex) {
				//doen niks
			}
		}
		//              Christie reel elf

		try {
			//links veld
			curY += height * 1.5;
			cIndent = Float.valueOf(String.valueOf(indent)).floatValue() / 5;
			g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 10));
			layout = new TextLayout("E-Mail:", g2d.getFont(), frc);
			height = layout.getBounds().getHeight();
			layout.draw(g2d, cIndent, (float) curY);

			//links waarde
			g2d.setFont(g2d.getFont().deriveFont(Font.ITALIC, 10));
			if (client.getPostTo() == 0) {
				layout =
							new TextLayout(client.getHomeEmail(), g2d.getFont(), frc);
			}
			else {
				layout =
							new TextLayout(client.getWorkEmail(), g2d.getFont(), frc);
			}

			layout.draw(g2d, (float) indent, (float) curY);

		}
		catch (IllegalArgumentException e) {
		}
		catch (NullPointerException ex) {
			//doen niks
		}
//regs veld
		try {
			g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 10));
			String text = "Total Price:";
			layout = new TextLayout(text, g2d.getFont(), frc);
			height = layout.getBounds().getHeight();
			layout.draw(
						g2d,
						(float) ( (pageWidth / 2) + cIndent - 25),
						(float) curY);
		}
		catch (IllegalArgumentException e) {
		}
		catch (NullPointerException ex) {
			//doen niks
		}
		try {

			//regs waarde
			float totalOne = 0;
			if (this.busPrefs.getVATReg().equals("Yes")) {
				totalOne = getPriceOne() * (1 + this.busPrefs.getVATPerc() / 100) *
							(100 - getDiscountOne()) / 100;

			}
			else {
				totalOne = getPriceOne() *
							(100 - getDiscountOne()) / 100;

			}
			float totalNo = totalOne * getNoOrdered();
			g2d.setFont(g2d.getFont().deriveFont(Font.ITALIC, 10));
			float totalAll = 0;
			if (this.busPrefs.getVATReg().equals("Yes")) {
				totalAll = totalNo +
							getPriceOther() * (1 + this.getBusPrefs().getVATPerc() / 100) *
							(100 - getDiscountOther()) /
							100;

			}
			else {
				totalAll = getPriceOther() * (100 - getDiscountOther()) /
							100 + totalNo;

			}


			String text = "R" + Utils.getCurrencyFormat(totalAll);
			layout = new TextLayout(text, g2d.getFont(), frc);
			layout.draw(
						g2d,
						(float) (pageWidth / 2) + (float) rindent,
						(float) curY);

		}
		catch (IllegalArgumentException e) {
		}
		catch (NullPointerException ex) {
			//doen niks
		}

	}


	private void printSnyLys(Graphics g, PageFormat format, int pageIndex) {
		//clear(g);
		Graphics2D g2d = (Graphics2D) g;

		g2d.translate(format.getImageableX(), format.getImageableY());
		g2d.setPaint(Color.black);
		double pageWidth = format.getImageableWidth();
		double pageHeight = format.getImageableHeight();
		double curY = 0;

		g2d.setFont(g2d.getFont().deriveFont(Font.BOLD + Font.ITALIC, 30));
		FontRenderContext frc = g2d.getFontRenderContext();

		try {
			TextLayout layout =
						new TextLayout(UserSettings.ownerCompany, g2d.getFont(), frc);
			Rectangle2D bounds = layout.getBounds();
			curY += bounds.getHeight();
			layout.draw(
						g2d,
						(float) (pageWidth / 2 - bounds.getWidth() / 2),
						(float) curY);
			curY += bounds.getHeight() * 1.5;
		}
		catch (IllegalArgumentException e) {
		}

		g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 9));
		try {
			String text = "";
			if (UserSettings.ownerTel != null
						&& !UserSettings.ownerTel.equals("")) {
				text += "Tel: " + UserSettings.ownerTel;
			}
			if (UserSettings.ownerFax != null
						&& !UserSettings.ownerFax.equals("")) {
				text += "  " + "Fax: " + UserSettings.ownerFax;
			}
			if (UserSettings.ownerCel != null
						&& !UserSettings.ownerCel.equals("")) {
				text += "  " + "Cel: " + UserSettings.ownerCel;
			}
			if (UserSettings.ownerEmail != null
						&& !UserSettings.ownerEmail.equals("")) {
				text += "  " + "E-mail: " + UserSettings.ownerEmail;
			}
			TextLayout layout = new TextLayout(text, g2d.getFont(), frc);
			Rectangle2D bounds = layout.getBounds();
			curY += bounds.getHeight();
			layout.draw(
						g2d,
						(float) (pageWidth / 2 - bounds.getWidth() / 2),
						(float) curY - 25);
			curY += bounds.getHeight();
		}
		catch (IllegalArgumentException e) {
		}
		catch (NullPointerException ex) {
			//doen niks
		}

		try {
			String text = "";
			if (UserSettings.ownerAdd1 != null
						&& !UserSettings.ownerAdd1.equals("")) {
				text += UserSettings.ownerAdd1;
			}
			if (UserSettings.ownerAdd2 != null
						&& !UserSettings.ownerAdd2.equals("")) {
				text += "  " + UserSettings.ownerAdd2;
			}
			if (UserSettings.ownerAdd3 != null
						&& !UserSettings.ownerAdd3.equals("")) {
				text += "  " + UserSettings.ownerAdd3;
			}
			if (UserSettings.ownerCode != null
						&& !UserSettings.ownerCode.equals("")) {
				text += "  " + UserSettings.ownerCode;
			}
			TextLayout layout = new TextLayout(text, g2d.getFont(), frc);
			Rectangle2D bounds = layout.getBounds();
			curY += bounds.getHeight();
			layout.draw(
						g2d,
						(float) (pageWidth / 2 - bounds.getWidth() / 2),
						(float) curY - 25);
			curY += bounds.getHeight();

			curY -= 25;
			/*Line2D.Double l2d = new Line2D.Double(0, curY, pageWidth, curY);
			g2d = (Graphics2D) g;
			g2d.draw(l2d);*/
		}
		catch (IllegalArgumentException e) {
		}
		catch (NullPointerException ex) {
			//doen niks
		}



		try {
			Line2D.Double l2d = new Line2D.Double(0, curY, pageWidth, curY);
			g2d = (Graphics2D) g;
			g2d.draw(l2d);

			curY += 25;
			g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 10));
			String text = "Client Details:";

			TextLayout layout = new TextLayout(text, g2d.getFont(), frc);

			Rectangle2D bounds = layout.getBounds();

			layout.draw(g2d, (float) (0), (float) curY);

			text = "Design Details";
			layout = new TextLayout(text, g2d.getFont(), frc);
			bounds = layout.getBounds();

			layout.draw(g2d, (float) pageWidth / 2 - 25, (float) curY);

			curY += bounds.getHeight();
		}
		catch (IllegalArgumentException e) {
		}
		catch (NullPointerException ex) {
			//doen niks
		}

		g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 10));
		curY *= 1.1;
		TextLayout layout =
					new TextLayout("0123456789012345", g2d.getFont(), frc);
		double indent = layout.getBounds().getWidth() * 0.9;
		double height;

		//links veld
		ArtofDB db;
		ClientDets client = null;
		float cIndent = Float.valueOf(String.valueOf(indent)).floatValue() / 5;
		layout = new TextLayout("Name:", g2d.getFont(), frc);
		height = layout.getBounds().getHeight();
		layout.draw(g2d, cIndent, (float) curY);

		try {
			//Christie reel een
			db = ArtofDB.getCurrentDB();
			client = db.getClient(getClientID());

			//links waarde
			g2d.setFont(g2d.getFont().deriveFont(Font.ITALIC, 10));
			layout =
						new TextLayout(
									client.getName() + " " + client.getSurname(),
									g2d.getFont(),
									frc);
			layout.draw(g2d, (float) indent, (float) curY);
		}
		catch (IllegalArgumentException e) {
		}
		catch (NullPointerException ex) {
			//doen niks
		}
		try {

			//regs Veld
			g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 10));
			layout = new TextLayout("Design Code:", g2d.getFont(), frc);
			height = layout.getBounds().getHeight();
			layout.draw(
						g2d,
						(float) ( (pageWidth / 2) + cIndent - 25),
						(float) curY);
			//regs waarde
			g2d.setFont(g2d.getFont().deriveFont(Font.ITALIC, 10));
			layout =
						new TextLayout(String.valueOf(designID), g2d.getFont(), frc);
			layout.draw(
						g2d,
						(float) (pageWidth / 2) + (float) indent,
						(float) curY);
		}
		catch (IllegalArgumentException e) {
		}
		catch (NullPointerException ex) {
			//doen niks
		}

		// Christie reel twee

		try {
			//links veld
			curY += height * 1.5;
			cIndent = Float.valueOf(String.valueOf(indent)).floatValue() / 5;
			g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 10));
			layout = new TextLayout("Tel(Home):", g2d.getFont(), frc);
			height = layout.getBounds().getHeight();
			layout.draw(g2d, cIndent, (float) curY);

			//links waarde
			g2d.setFont(g2d.getFont().deriveFont(Font.ITALIC, 10));
			layout = new TextLayout(client.getHomeTel(), g2d.getFont(), frc);
			layout.draw(g2d, (float) indent, (float) curY);
		}
		catch (IllegalArgumentException e) {
		}
		catch (NullPointerException ex) {
			//doen niks
		}
		try {

			//regs Veld
			g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 10));
			layout = new TextLayout("Design Title:", g2d.getFont(), frc);
			height = layout.getBounds().getHeight();
			layout.draw(
						g2d,
						(float) ( (pageWidth / 2) + cIndent - 25),
						(float) curY);
			//regs waarde
			g2d.setFont(g2d.getFont().deriveFont(Font.ITALIC, 10));
			layout = new TextLayout(String.valueOf(title), g2d.getFont(), frc);
			layout.draw(
						g2d,
						(float) (pageWidth / 2) + (float) indent,
						(float) curY);
		}
		catch (IllegalArgumentException e) {
		}
		catch (NullPointerException ex) {
			//doen niks
		}

		//              Christie reel drie

		try {
			//links veld
			curY += height * 1.5;
			cIndent = Float.valueOf(String.valueOf(indent)).floatValue() / 5;
			g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 10));
			layout = new TextLayout("Tel(Work):", g2d.getFont(), frc);
			height = layout.getBounds().getHeight();
			layout.draw(g2d, cIndent, (float) curY);

			//links waarde
			g2d.setFont(g2d.getFont().deriveFont(Font.ITALIC, 10));
			layout = new TextLayout(client.getWorkTel(), g2d.getFont(), frc);
			layout.draw(g2d, (float) indent, (float) curY);
		}
		catch (IllegalArgumentException e) {
		}
		catch (NullPointerException ex) {
			//doen niks
		}
		try {

			//regs Veld
			g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 10));
			layout = new TextLayout("Design Date:", g2d.getFont(), frc);
			height = layout.getBounds().getHeight();
			layout.draw(
						g2d,
						(float) ( (pageWidth / 2) + cIndent - 25),
						(float) curY);
			//regs waarde
			g2d.setFont(g2d.getFont().deriveFont(Font.ITALIC, 10));
			layout =
						new TextLayout(
									String.valueOf(Utils.getDatumStr(desDate)),
									g2d.getFont(),
									frc);
			layout.draw(
						g2d,
						(float) (pageWidth / 2) + (float) indent,
						(float) curY);
		}
		catch (IllegalArgumentException e) {
		}
		catch (NullPointerException ex) {
			//doen niks
		}

		//              Christie reel vier

		try {
			//links veld
			curY += height * 1.5;
			cIndent = Float.valueOf(String.valueOf(indent)).floatValue() / 5;
			g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 10));
			layout = new TextLayout("Mobile:", g2d.getFont(), frc);
			height = layout.getBounds().getHeight();
			layout.draw(g2d, cIndent, (float) curY);

			//links waarde
			g2d.setFont(g2d.getFont().deriveFont(Font.ITALIC, 10));
			if (client.getPostTo() == 0) {
				layout =
							new TextLayout(client.getHomeCell(), g2d.getFont(), frc);
			}
			else {
				layout =
							new TextLayout(client.getWorkCell(), g2d.getFont(), frc);
			}
			layout.draw(g2d, (float) indent, (float) curY);
		}
		catch (IllegalArgumentException e) {
		}
		catch (NullPointerException ex) {
			//doen niks
		}
		try {

			//			regs Veld
			g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 10));
			layout = new TextLayout("Price:", g2d.getFont(), frc);
			height = layout.getBounds().getHeight();
			layout.draw(
						g2d,
						(float) ( (pageWidth / 2) + cIndent - 25),
						(float) curY);
			//regs waarde
			g2d.setFont(g2d.getFont().deriveFont(Font.ITALIC, 10));
			layout =
						new TextLayout(
									"R"
									+ priceOne
									+ " excludes"
									+ busPrefs.getVATPerc()
									+ " VAT",
									g2d.getFont(),
									frc);
			layout.draw(
						g2d,
						(float) (pageWidth / 2) + (float) indent,
						(float) curY);

		}
		catch (IllegalArgumentException e) {
		}
		catch (NullPointerException ex) {
			//doen niks
		}

		//              Christie reel vyf

		try {
			//links veld
			curY += height * 1.5;
			cIndent = Float.valueOf(String.valueOf(indent)).floatValue() / 5;
			g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 10));
			layout = new TextLayout("Fax:", g2d.getFont(), frc);
			height = layout.getBounds().getHeight();
			layout.draw(g2d, cIndent, (float) curY);

			//links waarde
			g2d.setFont(g2d.getFont().deriveFont(Font.ITALIC, 10));
			if (client.getPostTo() == 0) {
				layout =
							new TextLayout(client.getHomeFax(), g2d.getFont(), frc);
			}
			else {
				layout =
							new TextLayout(client.getWorkFax(), g2d.getFont(), frc);
			}
			layout.draw(g2d, (float) indent, (float) curY);
		}
		catch (IllegalArgumentException e) {
		}
		catch (NullPointerException ex) {
			//doen niks
		}
		try {

			//regs Veld
			g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 10));
			layout = new TextLayout("Total Price:", g2d.getFont(), frc);
			height = layout.getBounds().getHeight();
			layout.draw(
						g2d,
						(float) ( (pageWidth / 2) + cIndent - 25),
						(float) curY);
			//regs waarde
			g2d.setFont(g2d.getFont().deriveFont(Font.ITALIC, 10));
			float totalOne = 0;
			if (busPrefs.getVATReg().equals("Yes")) {
				totalOne =
							getPriceOne()
							* (1 + busPrefs.getVATPerc() / 100)
							* (100 - getDiscountOne())
							/ 100;
			}
			else {
				totalOne =
							getPriceOne()
							* (100 - getDiscountOne())
							/ 100;
			}

			String text = "R" + Utils.getCurrencyFormat(totalOne);
			text += " includes "
						+ Utils.getCurrencyFormat(discountOne)
						+ "% discount";
			layout = new TextLayout(text, g2d.getFont(), frc);
			layout.draw(
						g2d,
						(float) (pageWidth / 2) + (float) indent,
						(float) curY);

		}
		catch (IllegalArgumentException e) {
		}
		catch (NullPointerException ex) {
			//doen niks
		}

		//              Christie reel ses

		try {
			//links veld
			curY += height * 1.5;
			cIndent = Float.valueOf(String.valueOf(indent)).floatValue() / 5;
			g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 10));
			layout = new TextLayout("Address:", g2d.getFont(), frc);
			height = layout.getBounds().getHeight();
			layout.draw(g2d, cIndent, (float) curY);

			//links waarde
			g2d.setFont(g2d.getFont().deriveFont(Font.ITALIC, 10));
			if (client.getPostTo() == 0) {
				layout =
							new TextLayout(client.getHomeAdd1(), g2d.getFont(), frc);
			}
			else {
				layout =
							new TextLayout(client.getWorkAdd1(), g2d.getFont(), frc);
			}
			layout.draw(g2d, (float) indent, (float) curY);

		}
		catch (IllegalArgumentException e) {
		}
		catch (NullPointerException ex) {
			//doen niks
		}

		//              Christie reel sewe

		try {
			//links veld
			curY += height * 1.5;
			cIndent = Float.valueOf(String.valueOf(indent)).floatValue() / 5;
			g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 10));
			g2d.setColor(Color.WHITE);
			layout = new TextLayout("asa0 ", g2d.getFont(), frc);
			height = layout.getBounds().getHeight();
			layout.draw(g2d, cIndent, (float) curY);
			g2d.setColor(Color.BLACK);

			//links waarde
			g2d.setFont(g2d.getFont().deriveFont(Font.ITALIC, 10));
			if (client.getPostTo() == 0) {
				layout =
							new TextLayout(client.getHomeAdd2(), g2d.getFont(), frc);
			}
			else {
				layout =
							new TextLayout(client.getWorkAdd2(), g2d.getFont(), frc);
			}
			layout.draw(g2d, (float) indent, (float) curY);
		}
		catch (IllegalArgumentException e) {
		}
		catch (NullPointerException ex) {
			//doen niks
		}
		try {

			//regs Veld

			float totalOne = 0;
			if (busPrefs.getVATReg().equals("Yes")) {
				totalOne =
							getPriceOne()
							* (1 + busPrefs.getVATPerc() / 100)
							* (100 - getDiscountOne())
							/ 100;
			}
			else {
				totalOne =
							getPriceOne()
							* (100 - getDiscountOne())
							/ 100;
			}

			//curY += height * 1.5;
			g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 10));
			String text = "Price for " + noOrdered + " items:";
			layout = new TextLayout(text, g2d.getFont(), frc);
			height = layout.getBounds().getHeight();
			layout.draw(
						g2d,
						(float) ( (pageWidth / 2) + cIndent - 25),
						(float) curY);
			g2d.setFont(g2d.getFont().deriveFont(Font.ITALIC, 10));
			text = "R" + Utils.getCurrencyFormat(totalOne * noOrdered);
			layout = new TextLayout(text, g2d.getFont(), frc);
			layout.draw(
						g2d,
						(float) (pageWidth / 2) + (float) indent,
						(float) curY);

		}
		catch (IllegalArgumentException e) {
		}
		catch (NullPointerException ex) {
			//doen niks
		}

		//              Christie reel agt

		try {
			//links veld
			curY += height * 1.5;
			cIndent = Float.valueOf(String.valueOf(indent)).floatValue() / 5;
			g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 10));
			g2d.setColor(Color.WHITE);
			layout = new TextLayout("asa1 ", g2d.getFont(), frc);

			height = layout.getBounds().getHeight();
			layout.draw(g2d, cIndent, (float) curY);
			g2d.setColor(Color.BLACK);

			//links waarde
			g2d.setFont(g2d.getFont().deriveFont(Font.ITALIC, 10));
			if (client.getPostTo() == 0) {
				layout =
							new TextLayout(client.getHomeAdd3(), g2d.getFont(), frc);
			}
			else {
				layout =
							new TextLayout(client.getWorkAdd3(), g2d.getFont(), frc);
			}

			layout.draw(g2d, (float) indent, (float) curY);
		}
		catch (IllegalArgumentException e) {
		}
		catch (NullPointerException ex) {
			//doen niks
		}
		try {

			//regs Veld
			g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 10));
			layout = new TextLayout("Status:", g2d.getFont(), frc);
			height = layout.getBounds().getHeight();
			layout.draw(
						g2d,
						(float) ( (pageWidth / 2) + cIndent - 25),
						(float) curY);
			//regs waarde
			g2d.setFont(g2d.getFont().deriveFont(Font.ITALIC, 10));

			layout = new TextLayout(status, g2d.getFont(), frc);
			layout.draw(
						g2d,
						(float) (pageWidth / 2) + (float) indent,
						(float) curY);
		}
		catch (IllegalArgumentException e) {
		}
		catch (NullPointerException ex) {
			//doen niks
		}

		//              Christie reel tien

		try {
			//links veld
			curY += height * 1.5;
			cIndent = Float.valueOf(String.valueOf(indent)).floatValue() / 5;
			g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 10));
			g2d.setColor(Color.WHITE);
			layout = new TextLayout("asa2", g2d.getFont(), frc);
			height = layout.getBounds().getHeight();
			layout.draw(g2d, cIndent, (float) curY);
			g2d.setColor(Color.BLACK);

			//links waarde
			g2d.setFont(g2d.getFont().deriveFont(Font.ITALIC, 10));
			if (client.getPostTo() == 0) {
				layout =
							new TextLayout(client.getHomeAdd4(), g2d.getFont(), frc);
			}
			else {
				layout =
							new TextLayout(client.getWorkAdd4(), g2d.getFont(), frc);
			}

			layout.draw(g2d, (float) indent, (float) curY);

		}
		catch (IllegalArgumentException e) {
		}
		catch (NullPointerException ex) {
			//doen niks
		}
		try {

			g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 10));
			layout = new TextLayout("Full Bottoms:", g2d.getFont(), frc);
			layout.draw(
						g2d,
						(float) pageWidth / 2 + cIndent - 25,
						(float) curY);

			g2d.setFont(g2d.getFont().deriveFont(Font.ITALIC, 10));
			if (methodPrefs.getFullBottomsBool()) {
				layout = new TextLayout("Yes", g2d.getFont(), frc);
			}
			else {
				layout = new TextLayout("No", g2d.getFont(), frc);
			}
			layout.draw(g2d, (float) (indent + pageWidth / 2), (float) curY);
		}
		catch (IllegalArgumentException e) {
		}
		catch (NullPointerException ex) {
			//doen niks
		}

		//              Christie reel elf

		try {
			//links veld
			curY += height * 1.5;
			cIndent = Float.valueOf(String.valueOf(indent)).floatValue() / 5;
			g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 10));
			layout = new TextLayout("Code:", g2d.getFont(), frc);
			height = layout.getBounds().getHeight();
			layout.draw(g2d, cIndent, (float) curY);

			//links waarde
			g2d.setFont(g2d.getFont().deriveFont(Font.ITALIC, 10));
			if (client.getPostTo() == 0) {
				layout =
							new TextLayout(client.getHomeCode(), g2d.getFont(), frc);
			}
			else {
				layout =
							new TextLayout(client.getWorkCode(), g2d.getFont(), frc);
			}

			layout.draw(g2d, (float) indent, (float) curY);
		}
		catch (IllegalArgumentException e) {
		}
		catch (NullPointerException ex) {
			//doen niks
		}
		try {

			//regs Veld
			if (noOrdered > 1) {

				g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 10));
				layout = new TextLayout("Border Type:", g2d.getFont(), frc);
				height = layout.getBounds().getHeight();
				layout.draw(
							g2d,
							(float) ( (pageWidth / 2) + cIndent - 25),
							(float) curY);
				//regs waarde

				g2d.setFont(g2d.getFont().deriveFont(Font.ITALIC, 10));
				layout =
							new TextLayout(
										methodPrefs.BORDER_TYPE[methodPrefs.getMethodType()],
										g2d.getFont(),
										frc);
				layout.draw(
							g2d,
							(float) (pageWidth / 2) + (float) indent,
							(float) curY);
			}
		}
		catch (IllegalArgumentException e) {
		}
		catch (NullPointerException ex) {
			//doen niks
		}

		//              Christie reel twaalf

		try {
			//links veld
			curY += height * 1.5;
			cIndent = Float.valueOf(String.valueOf(indent)).floatValue() / 5;
			g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 10));
			layout = new TextLayout("E-Mail:", g2d.getFont(), frc);
			height = layout.getBounds().getHeight();
			layout.draw(g2d, cIndent, (float) curY);

			//links waarde
			g2d.setFont(g2d.getFont().deriveFont(Font.ITALIC, 10));
			if (client.getPostTo() == 0) {
				layout =
							new TextLayout(client.getHomeEmail(), g2d.getFont(), frc);
			}
			else {
				layout =
							new TextLayout(client.getWorkEmail(), g2d.getFont(), frc);
			}

			layout.draw(g2d, (float) indent, (float) curY);

		}
		catch (IllegalArgumentException e) {
		}
		catch (NullPointerException ex) {
			//doen niks
		}


		// print die fokken prent van die fokken design

		// kry eers faktor om die fokken ding te scale
		double scaleFactor = 1.0;
		double imageHeight = 300.0;
		try {
			DesignItem2 frame = (DesignItem2)itemList.getFirst();
			double curWidth = frame.getOuterWidth();
			double curHeight = frame.getOuterHeight();
			Iterator it = itemList.iterator();
			while (it.hasNext()) {
				DesignItem2 item = (DesignItem2)it.next();
				if (item.getOuterWidth() > curWidth && item.getOuterHeight() > curHeight) {
					curWidth = item.getOuterWidth();
					curHeight = item.getOuterHeight();
				}
			}
			double factX = (pageWidth - 2.0 * 80.0) / curWidth;
			double factY = (imageHeight - 2.0 * 50.0) / curHeight;
			scaleFactor = Math.min(factX, factY);

		} catch (Exception e) {
			scaleFactor = 1.0;
		}

		try {
			curY += height * 2.0;
			double centerX = pageWidth / 2;
			double centerY = curY + imageHeight / 2.0;

			g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 10));
			cIndent = Float.valueOf(String.valueOf(indent)).floatValue() / 5;
			drawSimText(g2d, "mm", centerX + indent * 2, curY - height);

			int offsetCountLeft = 0, offsetCountRight = 0, offsetCountTop = 0, offsetCountBottom = 0;
			double prevLeftCenter = 0, prevRightCenter = 0, prevTopCenter = 0, prevBottomCenter = 0;

			for (int i = itemList.size() - 1; i >= 0; i--) {
				DesignItem2 item = (DesignItem2)itemList.get(i);
				g2d.setStroke(new BasicStroke(0.1f));
				item.printSimbool(g2d, centerX, centerY, scaleFactor);

				// teken afmetingwaardes
				double textY = curY + height * 2.0 + 10;
				double textX = 50;
				if (item.getInnerWidth() <= 1.0) {
					if (i == 0) {
						drawSimText(g2d, Utils.getNumberFormat(item.getOuterWidth()), centerX, textY);
						drawSimText(g2d, Utils.getNumberFormat(item.getOuterHeight()), textX, centerY);
					}

				} else {
					double leftDist = centerX - (item.getOuterWidth()/2 - item.getLeftGap()/2) * scaleFactor;
					if (15 > Math.abs(leftDist - prevLeftCenter)) offsetCountLeft++;
					else offsetCountLeft = 0;
					prevLeftCenter = leftDist;
					drawSimText(g2d, Utils.getNumberFormat(item.getLeftGap()), leftDist, textY - 10*offsetCountLeft);

					double rightDist = centerX + (item.getOuterWidth()/2 - item.getRightGap()/2) * scaleFactor;
					if (15 > Math.abs(rightDist - prevRightCenter)) offsetCountRight++;
					else offsetCountRight = 0;
					prevRightCenter = rightDist;
					drawSimText(g2d, Utils.getNumberFormat(item.getRightGap()), rightDist, textY - 10*offsetCountRight);

					double topDist = centerY - (item.getOuterHeight()/2 - item.getTopGap()/2) * scaleFactor;
					if (10 > Math.abs(topDist - prevTopCenter)) offsetCountTop++;
					else offsetCountTop = 0;
					prevTopCenter = topDist;
					drawSimText(g2d, Utils.getNumberFormat(item.getTopGap()), textX - 15*offsetCountTop, topDist);

					double bottomDist = centerY + (item.getOuterHeight()/2 - item.getBottomGap()/2) * scaleFactor;
					if (10 > Math.abs(bottomDist - prevBottomCenter)) offsetCountBottom++;
					else offsetCountBottom = 0;
					prevBottomCenter = bottomDist;
					drawSimText(g2d, Utils.getNumberFormat(item.getBottomGap()), textX - 15*offsetCountBottom, bottomDist);

					if (item.getLeftOffset() > 1)
						drawSimText(g2d, Utils.getNumberFormat(item.getLeftOffset()),
												centerX - (item.getOuterWidth()/2 - item.getLeftGap() - item.getLeftOffset()/2) * scaleFactor, textY);

					if (item.getRightOffset() > 1)
						drawSimText(g2d, Utils.getNumberFormat(item.getRightOffset()),
												centerX + (item.getOuterWidth()/2 - item.getRightGap() - item.getRightOffset()/2) * scaleFactor, textY);

					if (item.getTopOffset() > 1)
						drawSimText(g2d, Utils.getNumberFormat(item.getTopOffset()), textX,
												centerY - (item.getOuterHeight()/2- item.getTopGap() - item.getTopOffset()/2) * scaleFactor);

					if (item.getBottomOffset() > 1)
						drawSimText(g2d, Utils.getNumberFormat(item.getBottomOffset()), textX,
												centerY + (item.getOuterHeight()/2 - item.getBottomGap() - item.getBottomOffset()/2) * scaleFactor);

				}

				// teken afmetinglyne
				int lineX = 70;
				int lineY = (int)(curY + height * 2.0 + 20);
				g2d.drawLine((int)(centerX - item.getOuterWidth() * scaleFactor / 2), lineY + 10,
										 (int)(centerX - item.getOuterWidth() * scaleFactor / 2), lineY);
				g2d.drawLine((int)(centerX + item.getOuterWidth() * scaleFactor / 2), lineY + 10,
										 (int)(centerX + item.getOuterWidth() * scaleFactor / 2), lineY);
				g2d.drawLine(lineX + 10, (int)(centerY - item.getOuterHeight() * scaleFactor / 2),
										 lineX, (int)(centerY - item.getOuterHeight() * scaleFactor / 2));
				g2d.drawLine(lineX + 10, (int)(centerY + item.getOuterHeight() * scaleFactor / 2),
										 lineX, (int)(centerY + item.getOuterHeight() * scaleFactor / 2));

				if (item.getLeftOffset() > 1)
					g2d.drawLine((int)(centerX - (item.getOuterWidth()/2 - item.getLeftGap()) * scaleFactor), lineY + 10,
											 (int)(centerX - (item.getOuterWidth()/2 - item.getLeftGap()) * scaleFactor), lineY);
				if (item.getRightOffset() > 1)
					g2d.drawLine((int)(centerX + (item.getOuterWidth()/2 - item.getRightGap()) * scaleFactor), lineY + 10,
											 (int)(centerX + (item.getOuterWidth()/2 - item.getRightGap()) * scaleFactor), lineY);
				if (item.getTopOffset() > 1)
					g2d.drawLine(lineX + 10, (int)(centerY - (item.getOuterHeight()/2 - item.getTopGap()) * scaleFactor),
											 lineX, (int)(centerY - (item.getOuterHeight()/2 - item.getTopGap()) * scaleFactor));
				if (item.getBottomOffset() > 1)
					g2d.drawLine(lineX + 10, (int)(centerY + (item.getOuterHeight()/2 - item.getBottomGap()) * scaleFactor),
											 lineX, (int)(centerY + (item.getOuterHeight()/2 - item.getBottomGap()) * scaleFactor));

				centerX = centerX + (item.getLeftGap() + item.getLeftOffset() - item.getRightGap() - item.getRightOffset()) * scaleFactor / 2;
				centerY = centerY + (item.getTopGap() + item.getTopOffset() - item.getBottomGap() - item.getBottomOffset()) * scaleFactor / 2;
			}
		} catch (NullPointerException e) {
			// doen niks
		}
		curY += imageHeight;



		// Print ItemList
		g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 10));
		curY += 15;
		layout = new TextLayout("01234567890", g2d.getFont(), frc);
		Rectangle2D bounds = layout.getBounds();
		double width1 = bounds.getWidth() - 8;
		layout = new TextLayout("012345678", g2d.getFont(), frc);
		bounds = layout.getBounds();
		double width2 = bounds.getWidth() - 8;
		layout = new TextLayout("01234567", g2d.getFont(), frc);
		bounds = layout.getBounds();
		double width3 = bounds.getWidth() - 8;

		double curX = addStringGap(g2d, frc, "Item", curY, 0, width3, false);
		curX = addStringGap(g2d, frc, "Item Code", curY, curX, width1, false);
		curX = addStringGap(g2d, frc, "Own Code", curY, curX, width1, false);
		curX = addStringGap(g2d, frc, "Width", curY, curX, width2, true);
		curX = addStringGap(g2d, frc, "Height", curY, curX, width2, true);
		curX = addStringGap(g2d, frc, "Top", curY, curX, width2, true);
		curX = addStringGap(g2d, frc, "Bottom", curY, curX, width2, true);
		curX = addStringGap(g2d, frc, "Left", curY, curX, width2, true);
		curX = addStringGap(g2d, frc, "Right", curY, curX, width2, true);
		curX = addStringGap(g2d, frc, "Tot Lgth", curY, curX, width2, true);

		g2d.setFont(g2d.getFont().deriveFont(Font.ITALIC, 10));
		//print lyn
		curY += 5;
		g2d.setStroke(new BasicStroke(2f));
		Line2D.Double l2d = new Line2D.Double(0, curY, pageWidth, curY);
		g2d = (Graphics2D) g;

		g2d.draw(l2d);

		curY += height * 1.5;
		Iterator it = itemList.iterator();
		while (it.hasNext()) {
			DesignItem2 item = (DesignItem2) it.next();
			curX = 0;
			curX = addStringGap(g2d, frc, item.getType(), curY, curX, width3, false);

			try {
				curX = addStringGap(g2d, frc, item.getItemCode().substring(0, 12), curY, curX, width1, false);
			} catch (Exception e) {
				curX = addStringGap(g2d, frc, item.getItemCode(), curY, curX, width1, false);
			}

			try {
				curX = addStringGap(g2d, frc, item.getOwnCode().substring(0, 12), curY, curX, width1, false);
			} catch (Exception e) {
				curX = addStringGap(g2d, frc, item.getOwnCode(), curY, curX, width1, false);
			}

			if (item.getDesignType() == Designer.ITEM_FRAME) {
				double width = item.getDesignWidth() - (item.getRightGap() + item.getLeftGap()) + 2 * item.getMaterialDets().getDefaultValuesWithInMaterialDets().getRebate();
				curX = addStringGap(g2d, frc, Utils.getCurrencyFormat(width), curY, curX, width2, true);
				double length = item.getDesignHeight() - (item.getTopGap() + item.getBottomGap()) + 2 * item.getMaterialDets().getDefaultValuesWithInMaterialDets().getRebate();
				curX = addStringGap(g2d, frc, Utils.getCurrencyFormat(length), curY, curX, width2, true);
				curX = addStringGap(g2d, frc, "", curY, curX, width2, true);
				curX = addStringGap(g2d, frc, "", curY, curX, width2, true);
				curX = addStringGap(g2d, frc, "", curY, curX, width2, true);
				curX = addStringGap(g2d, frc, "", curY, curX, width2, true);
				double totLength = 2.0 * item.getDesignHeight() + 2.0 * item.getDesignWidth();
				curX = addStringGap(g2d, frc, Utils.getCurrencyFormat(totLength), curY, curX, width2, true);

			} else if (item.getDesignType() == Designer.ITEM_SLIP) {
				double width = item.getDesignWidth() - 2 * item.getMaterialDets().getDefaultValuesWithInMaterialDets().getRebate();
				curX = addStringGap(g2d, frc, Utils.getCurrencyFormat(width), curY, curX, width2, true);
				double length = item.getDesignHeight() - 2 * item.getMaterialDets().getDefaultValuesWithInMaterialDets() .getRebate();
				curX = addStringGap(g2d, frc, Utils.getCurrencyFormat(length), curY, curX, width2, true);
				curX = addStringGap(g2d, frc, "", curY, curX, width2, true);
				curX = addStringGap(g2d, frc, "", curY, curX, width2, true);
				curX = addStringGap(g2d, frc, "", curY, curX, width2, true);
				curX = addStringGap(g2d, frc, "", curY, curX, width2, true);
				double totLength = 2.0 * item.getDesignHeight() + 2.0 * item.getDesignWidth();
				curX = addStringGap(g2d, frc, Utils.getCurrencyFormat(totLength), curY, curX, width2, true);

			} else {
				curX = addStringGap(g2d, frc, Utils.getCurrencyFormat(item.getDesignWidth()), curY, curX, width2, true);
				curX = addStringGap(g2d, frc, Utils.getCurrencyFormat(item.getDesignHeight()), curY, curX, width2, true);
				curX = addStringGap(g2d, frc, Utils.getCurrencyFormat(item.getDesignTopGap()), curY, curX, width2, true);
				curX = addStringGap(g2d, frc, Utils.getCurrencyFormat(item.getDesignBottomGap()), curY, curX, width2, true);
				curX = addStringGap(g2d, frc, Utils.getCurrencyFormat(item.getDesignLeftGap()), curY, curX, width2, true);
				curX = addStringGap(g2d, frc, Utils.getCurrencyFormat(item.getDesignRightGap()), curY, curX, width2, true);
			}
			curY += height * 1.5;
		}
		//jScrollPane.repaint();
	}

	private void printFaktuur(Graphics g, PageFormat format, int pageIndex) {
		//clear(g);
		Graphics2D g2d = (Graphics2D) g;

		g2d.translate(format.getImageableX(), format.getImageableY());
		g2d.setPaint(Color.black);
		double pageWidth = format.getImageableWidth();
		double pageHeight = format.getImageableHeight();
		double curY = 0;

		g2d.setFont(g2d.getFont().deriveFont(Font.BOLD + Font.ITALIC, 30));
		FontRenderContext frc = g2d.getFontRenderContext();
    //Datum
		try {
			curY += 25;
			g2d.setFont(g2d.getFont().deriveFont(Font.ITALIC, 10));
			String text = Utils.getDatumStr(Utils.getCurrentDate());

			TextLayout layout = new TextLayout(text, g2d.getFont(), frc);

			Rectangle2D bounds = layout.getBounds();
			//curY += bounds.getHeight();
			layout.draw(g2d,(float) 10,(float) curY);
		}
		catch (IllegalArgumentException e) {
		}

		//Datum
		try {
			g2d.setFont(g2d.getFont().deriveFont(Font.ITALIC, 10));
			String text = Utils.getCurrentDate() + "-" + UserSettings.INVOICE_NUMBER;

			TextLayout layout = new TextLayout("No. " + text, g2d.getFont(), frc);

			Rectangle2D bounds = layout.getBounds();
			//curY += bounds.getHeight();
			layout.draw(g2d,(float) 380,(float) curY);

			curY += bounds.getHeight();
		}
		catch (IllegalArgumentException e) {
		}



		// TAX INVOICE
		try {
			curY += 25;
			g2d.setFont(g2d.getFont().deriveFont(Font.ITALIC, 20));
			String text = "Tax Invoice";

			TextLayout layout = new TextLayout(text, g2d.getFont(), frc);

			Rectangle2D bounds = layout.getBounds();
			//curY += bounds.getHeight();
			layout.draw(g2d,(float) ( (pageWidth / 2) -(layout.getBounds().getWidth() / 2)),(float) curY);

			curY += bounds.getHeight();
		}
		catch (IllegalArgumentException e) {
		}

		try {
			curY += 25;
			g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 12));
			String text = "FROM:";

			TextLayout layout = new TextLayout(text, g2d.getFont(), frc);

			Rectangle2D bounds = layout.getBounds();

			layout.draw(g2d, (float) (0), (float) curY);

			text = "TO:";
			layout = new TextLayout(text, g2d.getFont(), frc);
			bounds = layout.getBounds();

			layout.draw(g2d, (float) pageWidth / 2 - 25, (float) curY);

			curY += bounds.getHeight() + 5;
		}
		catch (IllegalArgumentException e) {
		}
		catch (NullPointerException ex) {
			//doen niks
		}
		g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 10));
		curY *= 1.1;
		TextLayout layout = new TextLayout("0123456789012345", g2d.getFont(), frc);
		double indent = layout.getBounds().getWidth() * 0.9;
		double height;

		//links veld

    if (client == null) {
			DesignDets designDets = (DesignDets) designList.get(0);
			busPrefs = designDets.getBusPrefs();
			client = ArtofDB.getCurrentDB().getClient(designDets.getClientID());
		}


		//Christie reel een  Owner besigheid naam
		float cIndent = Float.valueOf(String.valueOf(indent)).floatValue() / 5;
		try {
			layout = new TextLayout(UserSettings.ownerCompany,	g2d.getFont(), frc);
		} catch (NullPointerException e) {
			layout = new TextLayout("",	g2d.getFont(), frc);
		}
		height = layout.getBounds().getHeight();
		layout.draw(g2d, cIndent, (float) curY);

		try {
			//regs Veld  -  Klient Naam
			g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 10));
			layout = new TextLayout(client.getName() + " " + client.getSurname(), g2d.getFont(), frc);
			height = layout.getBounds().getHeight();
			layout.draw(g2d, (float) ( (pageWidth / 2) + cIndent - 25),	(float) curY);
		}
		catch (IllegalArgumentException e) {
		}
		catch (NullPointerException ex) {
			//doen niks
		}

		// Christie reel twee

		try {
			//links veld   - owner Address 1
			curY += height * 1.5;
			cIndent = Float.valueOf(String.valueOf(indent)).floatValue() / 5;
			g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 10));
			try {
				layout = new TextLayout(UserSettings.ownerAdd1, g2d.getFont(), frc);
			} catch (NullPointerException e) {
				layout = new TextLayout("", g2d.getFont(), frc);
			}
			height = layout.getBounds().getHeight();
			layout.draw(g2d, cIndent, (float) curY);
		}
		catch (IllegalArgumentException e) {
		}
		catch (NullPointerException ex) {
			//doen niks
		}
		try {

			//regs Veld   client Address 1
			g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 10));
			if (client.getPostTo() == 0)
				layout = new TextLayout(client.getHomeAdd1(), g2d.getFont(), frc);
			else
				layout = new TextLayout(client.getWorkAdd1(), g2d.getFont(), frc);
			height = layout.getBounds().getHeight();
			layout.draw(g2d,(float) ( (pageWidth / 2) + cIndent - 25),(float) curY);
		}
		catch (IllegalArgumentException e) {
		}
		catch (NullPointerException ex) {
			//doen niks
		}

		//              Christie reel drie
		try {
			//links veld   - owner Address 2
			curY += height * 1.5;
			cIndent = Float.valueOf(String.valueOf(indent)).floatValue() / 5;
			g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 10));
			try {
				layout = new TextLayout(UserSettings.ownerAdd2, g2d.getFont(), frc);
			} catch (NullPointerException e) {
				layout = new TextLayout("", g2d.getFont(), frc);
			}
			height = layout.getBounds().getHeight();
			layout.draw(g2d, cIndent, (float) curY);
		} catch (IllegalArgumentException e) {}
			catch (NullPointerException ex) {}

		try {
			//regs Veld  - klient address 2
			g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 10));
			if (client.getPostTo() == 0)
				layout = new TextLayout(client.getHomeAdd2(), g2d.getFont(), frc);
			else
				layout = new TextLayout(client.getWorkAdd2(), g2d.getFont(), frc);

			height = layout.getBounds().getHeight();
			layout.draw(g2d,(float) ( (pageWidth / 2) + cIndent - 25),(float) curY);
		}
		catch (IllegalArgumentException e) {}
		catch (NullPointerException ex) {}

		//              Christie reel vier
		try {
			//links veld owner address 3
			curY += height * 1.5;
			cIndent = Float.valueOf(String.valueOf(indent)).floatValue() / 5;
			g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 10));
			try {
				layout = new TextLayout(UserSettings.ownerAdd3, g2d.getFont(), frc);
			} catch (Exception e) {
				layout = new TextLayout("", g2d.getFont(), frc);
			}
			height = layout.getBounds().getHeight();
			layout.draw(g2d, cIndent, (float) curY);
		}
		catch (IllegalArgumentException e) {
		}
		catch (NullPointerException ex) {
			//doen niks
		}
		try {
			//			regs Veld  -  Klient address3
			g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 10));
			if (client.getPostTo() == 0)
				layout = new TextLayout(client.getHomeAdd3(), g2d.getFont(), frc);
			else
				layout = new TextLayout(client.getWorkAdd3(), g2d.getFont(), frc);

			height = layout.getBounds().getHeight();
			layout.draw(g2d,(float) ( (pageWidth / 2) + cIndent - 25),(float) curY);
		}
		catch (IllegalArgumentException e) {
		}
		catch (NullPointerException ex) {
			//doen niks
		}

		//              Christie reel vyf
		try {
			//links veld - owmer code
			curY += height * 1.5;
			cIndent = Float.valueOf(String.valueOf(indent)).floatValue() / 5;
			g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 10));
			try {
				layout = new TextLayout(UserSettings.ownerCode, g2d.getFont(), frc);
			} catch (NullPointerException e) {
				layout = new TextLayout("", g2d.getFont(), frc);
			}
			height = layout.getBounds().getHeight();
			layout.draw(g2d, cIndent, (float) curY);
		}
		catch (IllegalArgumentException e) {
		}
		catch (NullPointerException ex) {
			//doen niks
		}
		try {
			//regs Veld
			g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 10));
			if (client.getPostTo() == 0)
				layout = new TextLayout(client.getHomeAdd4(), g2d.getFont(), frc);
			else
				layout = new TextLayout(client.getWorkAdd4(), g2d.getFont(), frc);

			height = layout.getBounds().getHeight();
			layout.draw(g2d,(float) ( (pageWidth / 2) + cIndent - 25),(float) curY);
		}
		catch (IllegalArgumentException e) {
		}
		catch (NullPointerException ex) {
			//doen niks
		}

		//              Christie reel ses

		try {
			//links veld - OWner VAT
			curY += height * 1.5;
			cIndent = Float.valueOf(String.valueOf(indent)).floatValue() / 5;
			g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 10));
			layout = new TextLayout("VAT No. " + busPrefs.getVATCode(), g2d.getFont(), frc);
			height = layout.getBounds().getHeight();
			layout.draw(g2d, cIndent, (float) curY);
		}
		catch (IllegalArgumentException e) {
		}
		catch (NullPointerException ex) {
			//doen niks
		}
		try {
			//regs Veld
			g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 10));
			if (client.getPostTo() == 0)
				layout = new TextLayout(client.getHomeCode(), g2d.getFont(), frc);
			else
				layout = new TextLayout(client.getWorkCode(), g2d.getFont(), frc);

			/*height = layout.getBounds().getHeight();*/
			layout.draw(g2d,(float) ( (pageWidth / 2) + cIndent - 25),(float) curY);
		}
		catch (IllegalArgumentException e) {}
		catch (NullPointerException ex) {}

		//Client VATNo
		try {
			//regs Veld
			curY += height * 1.5;
			g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 10));
			layout = new TextLayout("Vat No. " + client.getDOB(), g2d.getFont(), frc);

			height = layout.getBounds().getHeight();
			layout.draw(g2d,(float) ( (pageWidth / 2) + cIndent - 25),(float) curY);
		}
		catch (IllegalArgumentException e) {}
		catch (NullPointerException ex) {}


    //banking details
		if (!UserSettings.ACCOUNT_OWNER.trim().equals("")) {
			try {
				//Header
				curY += height * 1.5;
				curY += height * 1.5;
				cIndent = Float.valueOf(String.valueOf(indent)).floatValue() / 5;
				g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 12));
				layout = new TextLayout("Banking Details:", g2d.getFont(), frc);
				height = layout.getBounds().getHeight();
				layout.draw(g2d, 0, (float) curY);

				//Account holder
				curY += height * 1.5;
				cIndent = Float.valueOf(String.valueOf(indent)).floatValue() / 5;
				g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 10));
				try {
					layout = new TextLayout("Account Holder:  " + UserSettings.ACCOUNT_OWNER, g2d.getFont(), frc);
				} catch (Exception e) {
					layout = new TextLayout("Account Holder:  " + "", g2d.getFont(), frc);
				}
				height = layout.getBounds().getHeight();
				layout.draw(g2d, (float )10, (float) curY);

				//Account Number
				curY += height * 1.5;
				cIndent = Float.valueOf(String.valueOf(indent)).floatValue() / 5;
				g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 10));
				try {
					layout = new TextLayout("Account Number:  " + UserSettings.ACCOUNT_NUMBER, g2d.getFont(), frc);
				} catch (Exception e) {
					layout = new TextLayout("Account Number:  " + "", g2d.getFont(), frc);
				}
				height = layout.getBounds().getHeight();
				layout.draw(g2d, (float )10, (float) curY);

				//Bank Name
				curY += height * 1.5;
				cIndent = Float.valueOf(String.valueOf(indent)).floatValue() / 5;
				g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 10));
				try {
					layout = new TextLayout("Bank:  " + UserSettings.BANK_NAME, g2d.getFont(), frc);
				} catch (Exception e) {
					layout = new TextLayout("Bank:  " + "", g2d.getFont(), frc);
				}
				height = layout.getBounds().getHeight();
				layout.draw(g2d, (float )10, (float) curY);

				//Bank Code
				curY += height * 1.5;
				cIndent = Float.valueOf(String.valueOf(indent)).floatValue() / 5;
				g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 10));
				try {
					layout = new TextLayout("Bank Code:  " + UserSettings.BANK_CODE, g2d.getFont(), frc);
				} catch (Exception e) {
					layout = new TextLayout("Bank Code:  " + "", g2d.getFont(), frc);
				}
				height = layout.getBounds().getHeight();
				layout.draw(g2d, (float )10, (float) curY);

			}
			catch (IllegalArgumentException e) {
			}
			catch (NullPointerException ex) {
				//doen niks
			}
		}

		// Print ItemList
		g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 10));
		curY += height * 3.5;
		//width1
		layout = new TextLayout("01234567890", g2d.getFont(), frc);
		Rectangle2D bounds = layout.getBounds();
		double width1 = bounds.getWidth() - 8;

		//width2
		layout = new TextLayout("012345678", g2d.getFont(), frc);
		bounds = layout.getBounds();
		double width2 = bounds.getWidth() - 8;

		//width3
		layout = new TextLayout("01234567", g2d.getFont(), frc);
		bounds = layout.getBounds();
		double width3 = bounds.getWidth() - 8;

		//width4
		layout = new TextLayout("0123456789001234", g2d.getFont(), frc);
		bounds = layout.getBounds();
		double width4 = bounds.getWidth() - 8;

		//width5
		layout = new TextLayout("01234567890123456789012345", g2d.getFont(), frc);
		bounds = layout.getBounds();
		double width5 = bounds.getWidth() - 8;

		//Headings
		double curX = addStringGap(g2d, frc, "Date", curY, 0, width1, false);
		curX = addStringGap(g2d, frc, "Design ID", curY, curX, width1, false);
		curX = addStringGap(g2d, frc, "Design Name", curY, curX, width5, false);
		curX = addStringGap(g2d, frc, "Status", curY, curX, width1, false);
		curX = addStringGap(g2d, frc, "Quantity", curY, curX, width1, true);
		curX = addStringGap(g2d, frc, "R", curY, curX, width4, true);

		g2d.setFont(g2d.getFont().deriveFont(Font.ITALIC, 10));
		//print lyn
		curY += 5;
		g2d.setStroke(new BasicStroke(2f));
		Line2D.Double l2d = new Line2D.Double(0, curY, pageWidth, curY);
		g2d = (Graphics2D) g;
 		g2d.draw(l2d);

		curY += height * 1.5;
		Iterator it = designList.iterator();
		double totalLabelX = 0.0;
		double totalValueX = 0.0;
		double vatTotal = 0.0;
		double discountTotal = 0.0;
		double discountOther = 0.0;
		double discountDesign = 0.0;

		while (it.hasNext()) {
			DesignDets design = (DesignDets) it.next();
			curX = 0;
			//Date
			curX = addStringGap(g2d, frc, Utils.getDatumStr(design.getDate()), curY, curX, width1, false);

			//designID
			curX = addStringGap(g2d, frc, String.valueOf(design.getDesignID()), curY, curX, width1, false);

			//design title
			try {
				curX = addStringGap(g2d, frc, design.getTitle(), curY, curX, width5, false);
			} catch (Exception e) {
				curX = addStringGap(g2d, frc, design.getTitle(), curY, curX, width5, false);
			}

			//design Status
			try {
				curX = addStringGap(g2d, frc, design.getStatus(), curY, curX, width1, false);
			} catch (Exception e) {
				curX = addStringGap(g2d, frc, design.getStatus(), curY, curX, width1, false);
			}

			//design Quantity
			try {
				totalLabelX = curX;
				curX = addStringGap(g2d, frc, String.valueOf(design.getNoOrdered()), curY, curX, width1, true);
			} catch (Exception e) {
				curX = addStringGap(g2d, frc, String.valueOf(design.getNoOrdered()), curY, curX, width1, true);
			}


			//design Price
			totalValueX = curX;
			curX = addStringGap(g2d, frc, String.valueOf((design.getPriceOne() * design.getNoOrdered()) + design.getPriceOther()), curY, curX, width4, true);
			vatTotal += (design.getPriceOne() * design.getNoOrdered()) + design.getPriceOther();

			if (design.getDiscountOne() != 0 && design.getPriceOne() != 0 && design.getNoOrdered() != 0) {
				if (busPrefs.isVATReg())
					discountDesign = ((design.getDiscountOne() / 100) * (design.getPriceOne() + (design.getPriceOne() *	(busPrefs.getVATPerc() / 100)))) * design.getNoOrdered();
				else
					discountDesign = ((design.getDiscountOne() / 100) * design.getPriceOne()) * design.getNoOrdered();

				discountTotal += discountDesign;
			}

			if (design.discountOther != 0 && design.getPriceOther() != 0) {
				if (busPrefs.isVATReg())
					discountOther = (design.getDiscountOther() / 100) * (design.getPriceOne() + (design.getPriceOne() *	(busPrefs.getVATPerc() / 100)));
				else
					discountOther = (design.getDiscountOther() / 100) * design.getPriceOne();

				discountTotal += discountOther;
			}

			curY += height * 1.5;
		}

		//VAT Deel
		curY += height * 1.5;
		curX = addStringGap(g2d, frc, "___________", curY, totalValueX, width4, true);
		curY += height * 1.5;
		curX = addStringGap(g2d, frc, Utils.getCurrencyFormat(vatTotal), curY, totalValueX, width4, true);
		curY += height * 1.5;
		if (busPrefs.isVATReg()) {
			curX = addStringGap(g2d, frc, String.valueOf("+ VAT @ " + busPrefs.getVATPerc()), curY, totalLabelX, width1, true);
			curX = addStringGap(g2d, frc, String.valueOf(Utils.getCurrencyFormat(vatTotal * (busPrefs.getVATPerc() / 100))), curY, totalValueX, width4, true);
		}
		else {
			curX = addStringGap(g2d, frc, String.valueOf("+ VAT @ 0%"), curY, totalLabelX, width1, true);
			curX = addStringGap(g2d, frc, String.valueOf(0), curY, totalValueX, width4, true);
		}
    curY += height * 1.5;
		curX = addStringGap(g2d, frc, String.valueOf("- Total Discount " ), curY, totalLabelX, width1, true);
		curX = addStringGap(g2d, frc, String.valueOf(Utils.getCurrencyFormat(discountTotal)), curY, totalValueX, width4, true);

		curY += height * 0.5;
		curX = addStringGap(g2d, frc, "___________", curY, totalValueX, width4, true);
		curY += height * 1.5;
		curX = addStringGap(g2d, frc, "Total ", curY, totalLabelX, width1, true);
		if (busPrefs.isVATReg())
			curX = addStringGap(g2d, frc, String.valueOf(Utils.getCurrencyFormat((vatTotal + vatTotal * (busPrefs.getVATPerc() / 100)) - discountTotal)), curY, totalValueX, width4, true);
		else
			curX = addStringGap(g2d, frc, String.valueOf(Utils.getCurrencyFormat(vatTotal - discountTotal)), curY, totalValueX, width4, true);

		curX = addStringGap(g2d, frc, "___________", curY, totalValueX, width4, true);
		curY += height * 0.2;
		curX = addStringGap(g2d, frc, "___________", curY, totalValueX, width4, true);
		//jScrollPane.repaint();
	}


	private void drawSimText(Graphics2D big, String value, double x, double y) {
		Font font = new Font("Times New Roman", Font.PLAIN, 9);
		FontRenderContext frc = big.getFontRenderContext();
		TextLayout layout = new TextLayout(value, font, frc);
		Rectangle2D bounds = layout.getBounds();
		y += bounds.getHeight() / 2;
		x -= bounds.getWidth() / 2;
		layout.draw(big, (float)x, (float)y);
	}

	public class PrintFrame
				extends JFrame {
		PageFormat format;
		Graphics g;
		int pageIndex;
		public PrintFrame(Graphics g, PageFormat f, int pageIndex) {
			format = f;
			this.g = g;
			this.pageIndex = pageIndex;
			MyPanel myPan = new MyPanel();
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(myPan);
			jScrollPane.setVerticalScrollBarPolicy(
						JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			this.setSize(700, 500);
			this.getContentPane().setLayout(new BorderLayout());
			this.getContentPane().add(jScrollPane, BorderLayout.CENTER);
			this.setVisible(true);
		}

		public class MyPanel
					extends JPanel {

			public MyPanel() {
				this.setBackground(Color.WHITE);

				//this.setVisible(true);
			}

			public void clear(Graphics g) {
				super.paintComponent(g);
			}

			public void paintComponent(Graphics g) {
				clear(g);
				if (status.equals("In Order")) {
					printSnyLys(g, format, pageIndex);
				}
				else if (status.equals("Quotation")) {
					printQuotation(g, format, pageIndex);
				}
				else if (status.equals("Completed")) {
					printFaktuur(g, format, pageIndex);
				}
				jScrollPane.repaint();
			}

		}

	}

}
