package utility;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import javax.swing.JFrame;

import condition.Condition;
import condition.Interval;
import condition.ValueHolder;
import mvc.Controller;
import mvc.Model;
import mvc.View;

/**
 * ユーティリティの例題プログラム。
 * オブザーバ・デザインパターン(MVC: Model-View-Controller)を用いた典型的(模範的)なプログラム。
 */
public class Example extends Object
{
	/**
	 * 画像をファイルに書き出す際の番号。
	 */
	private static Integer fileNo = 0;

	/**
	 * ウィンドウの表示位置。
	 */
	private static Point displayPoint = new Point(30, 50);

	/**
	 * ウィンドウをずらして表示してゆく際の支距。
	 */
	private static Point offsetPoint = new Point(25, 25);

	/**
	 * ユーティリティの例題プログラム群を実行する。
	 * @param arguments 引数の文字列の配列
	 */
	public static void main(String[] arguments)
	{
		String aString = null;
		StringBuffer aBuffer = null;

		// CSVファイルを読み込む。
		aString = "SampleTexts".concat(File.separator.concat("PrimeMinisters.csv"));
		aBuffer = new StringBuffer();
		aBuffer.append(FileUtility.currentDirectory());
		aBuffer.append(aString);
		aString = aBuffer.toString();

		List<String> listOfLines = StringUtility.readTextFromFile(aString);
		listOfLines.forEach((String line) ->
		{
			System.out.printf("%s%n", line);
			List<String> items = StringUtility.splitString(line, ",");
			items.forEach((String item) ->
			{
				System.out.printf("%s%n", item);
			});
		});

		List<List<String>> aCollection = StringUtility.readRowsFromFile(aString);

		// 読み込んだCSVを一行ずつ標準出力へ書き出す。
		aCollection.forEach((List<String> aRow) ->
		{
			new Interval<Integer>(0,
			                      (Integer it) -> it < aRow.size(),
			                      (Integer it) -> it + 1
			).forEach((Integer it) ->
			{
				new Condition(() -> it > 0).ifTrue(() -> { System.out.print(","); });
				String string = aRow.get(it);
				string = StringUtility.csvString(string);
				System.out.print(string);
			});
			System.out.println();
		});

		// CSVファイルを書き込む。
		aString = "SampleTexts".concat(File.separator.concat("PrimeMinisters2.csv"));
		aBuffer = new StringBuffer();
		aBuffer.append(FileUtility.currentDirectory());
		aBuffer.append(aString);
		aString = aBuffer.toString();
		StringUtility.writeRows(aCollection, aString);

		// 画像ファイルを読み込んで、ウィンドウの中に表示する。
		aString = "SampleImages".concat(File.separator.concat("CROWN.jpg"));
		aBuffer = new StringBuffer();
		aBuffer.append(System.getProperty("user.dir"));
		aBuffer.append(File.separator);
		aBuffer.append(aString);
		aString = aBuffer.toString();
		BufferedImage theImage = ImageUtility.readImage(aString);
		Example.open(theImage, "CROWN (Color)");

		// 読み込んだ画像をグレースケール画像に変換して、ウィンドウの中に表示する。
		BufferedImage anImage = ImageUtility.grayscaleImage(theImage);
		Example.open(anImage, "CROWN (Gray Scale)");

		// 読み込んだ画像を縮小（0.75倍の大きさに変換）して、ウィンドウの中に表示する。
		double factor = 0.75d;
		Integer width = (int)((double)(theImage.getWidth()) * factor);
		Integer height = (int)((double)(theImage.getHeight()) * factor);
		anImage = ImageUtility.adjustImage(theImage, width, height);
		Example.open(anImage, "CROWN (Shrinked)");

		// 読み込んだ画像を拡大（1.25倍の大きさに変換）して、ウィンドウの中に表示する。
		factor = 1.25d;
		width = (int)((double)(theImage.getWidth()) * factor);
		height = (int)((double)(theImage.getHeight()) * factor);
		anImage = ImageUtility.adjustImage(theImage, width, height);
		Example.open(anImage, "CROWN (Magnified)");

		// URL文字列で指定された画像を読み込んで、ウィンドウの中に表示する。
		aString = "http://aokilab.kyoto-su.ac.jp/documents/BlackBook/images/BlackBookFrontPage335x432.jpg";
		anImage = ImageUtility.readImageFromURL(aString);
		Example.open(anImage, "Black Book");

		// PDFファイルに関連付けられたアプリケーションを起動してファイルを開く。
		aString = "SamplePDFs".concat(File.separator.concat("三つの世界.key.pdf"));
		aBuffer = new StringBuffer();
		aBuffer.append(FileUtility.currentDirectory());
		aBuffer.append(aString);
		aString = aBuffer.toString();
		FileUtility.open(aString);

		return;
	}

	/**
	 * 描画画像(anImage)とラベル文字列(labelString)を指定してMVCを作り、表示位置(displayPoint)にウィンドウを開く。
	 * @param anImage 描画画像
	 * @param labelString ラベル文字列
	 */
	private static void open(BufferedImage anImage, String labelString)
	{
		Model aModel = new Model();
		aModel.picture(anImage);
		View aView = new View(aModel, new Controller());

		JFrame aWindow = new JFrame(labelString);
		aWindow.getContentPane().add(aView);
		Dimension aDimension = new Dimension(anImage.getWidth(), anImage.getHeight());
		aWindow.setMinimumSize(aDimension);
		aWindow.setMaximumSize(aDimension);
		aWindow.setResizable(false);
		aWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		aWindow.addNotify();
		Integer titleBarHeight = aWindow.getInsets().top;
		aWindow.setSize(aDimension.width, aDimension.height + titleBarHeight);
		aWindow.setLocation(displayPoint.x, displayPoint.y);
		aWindow.setVisible(true);

		Example.write(anImage);
		displayPoint = new Point(displayPoint.x + offsetPoint.x, displayPoint.y + offsetPoint.y);
		return;
	}

	/**
	 * 描画画像(anImage)をResultImagesというディレクトリの中に連番を付けて書き込む。
	 * @param anImage 描画画像
	 */
	private static void write(BufferedImage anImage)
	{
		File aDirectory = new File("ResultImages");
		new Condition(() -> aDirectory.exists() == false).ifTrue(() ->
		{
			try { aDirectory.mkdir(); }
			catch (SecurityException anException) { anException.printStackTrace(); }
		});
		ValueHolder<String> aString = new ValueHolder<String>(Integer.toString(fileNo++));
		new Interval<String>(aString.get(),
		                     (String it) -> it.length() < 2,
		                     (String it) -> "0".concat(it)).forEach();
		aString.setDo((String it) -> it.concat(".jpg"));
		aString.setDo((String it) -> "Utility".concat(it));
		aString.setDo((String it) -> File.separator.concat(it));
		aString.setDo((String it) -> aDirectory.getName().concat(it));
		ImageUtility.writeImage(anImage, aString.get());
		return;
	}
}
