package jp.co.sss.lms.ct.f03_report;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import jp.co.sss.lms.ct.util.WebDriverUtils;

/**
 * 結合テスト レポート機能
 * ケース09
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース09 受講生 レポート登録 入力チェック")
public class Case09 {

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {
		// 遷移先URL
		goTo("http://localhost:8080/lms");

		// ページタイトルの一致確認
		String title = WebDriverUtils.webDriver.getTitle();
		assertEquals("ログイン | LMS", title);

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		// 値の取得
		final WebElement login = webDriver.findElement(By.className("btn-primary"));
		final WebElement loginId = webDriver.findElement(By.id("loginId"));
		final WebElement password = webDriver.findElement(By.id("password"));

		// キー入力
		loginId.clear();
		loginId.sendKeys("StudentAA01");
		password.clear();
		password.sendKeys("studentAA01");

		// ログイン前のエビデンス取得
		getEvidence(new Object() {
		}, "before");

		// ログインボタンをクリック
		login.click();

		// ログイン後のエビデンス取得
		getEvidence(new Object() {
		}, "after");

		// 遷移先URLの一致確認
		String url = webDriver.getCurrentUrl();
		assertEquals("http://localhost:8080/lms/course/detail", url);
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test03() {

		// リンクを取得し、押下する
		final WebElement userLink = webDriver
				.findElement(By.cssSelector("a[href='/lms/user/detail']"));
		userLink.click();

		// 遷移後のエビデンス取得
		getEvidence(new Object() {
		});

		// 遷移後URLの一致確認
		String url = webDriver.getCurrentUrl();
		assertEquals("http://localhost:8080/lms/user/detail", url);
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 該当レポートの「修正する」ボタンを押下しレポート登録画面に遷移")
	void test04() {

		// ページ最下部までスクロール
		scrollTo("1000");

		// 遷移前のエビデンス取得
		getEvidence(new Object() {
		}, "bafore");

		// テーブルを取得
		List<WebElement> rows = webDriver.findElements(By.xpath("//*[@id='main']/table[3]/tbody/tr[td]"));

		// 週報【デモ】を探して修正するボタンを押下
		for (WebElement row : rows) {
			final WebElement examCell = row.findElement(By.xpath("./td[2]"));
			if (examCell.getText().equals("週報【デモ】")) {
				final WebElement detailBtn = row.findElement(By.xpath("./td[5]//input[@value='修正する']"));
				detailBtn.click();
				break;
			}
		}

		// 遷移後のエビデンス取得
		getEvidence(new Object() {
		}, "after");

		// 遷移後URLの一致確認
		String url = webDriver.getCurrentUrl();
		assertEquals("http://localhost:8080/lms/report/regist", url);
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しエラー表示：学習項目が未入力")
	void test05() {

		// 学習項目を未入力にする
		final WebElement learningItemsError = webDriver.findElement(By.id("intFieldName_0"));
		learningItemsError.clear();
		learningItemsError.sendKeys("");

		// 提出前のエビデンス取得
		getEvidence(new Object() {
		}, "before");

		// ページ最下部までスクロール
		scrollTo("1000");

		// ボタンを取得して押下
		final WebElement submitBtn = webDriver.findElement(By.cssSelector("button[type='submit']"));
		submitBtn.click();

		// 提出後のエビデンス取得
		getEvidence(new Object() {
		}, "after");

		// エラーメッセージの一致確認
		String errorMsg = webDriver.findElement(By.className("error")).getText();
		assertEquals("* 理解度を入力した場合は、学習項目は必須です。", errorMsg);

		// 学習項目を再入力
		final WebElement learningItems = webDriver.findElement(By.id("intFieldName_0"));
		learningItems.clear();
		learningItems.sendKeys("ITリテラシー①");
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：理解度が未入力")
	void test06() {

		// 理解度を未入力にする
		final WebElement understandingError = webDriver.findElement(By.id("intFieldValue_0"));
		Select selectError = new Select(understandingError);
		selectError.selectByVisibleText("");

		// 提出前のエビデンス取得
		getEvidence(new Object() {
		}, "before");

		// ページ最下部までスクロール
		scrollTo("1000");

		// ボタンを取得して押下
		final WebElement submitBtn = webDriver.findElement(By.cssSelector("button[type='submit']"));
		submitBtn.click();

		// 提出後のエビデンス取得
		getEvidence(new Object() {
		}, "after");

		// エラーメッセージの一致確認
		String errorMsg = webDriver.findElement(By.className("error")).getText();
		assertEquals("* 学習項目を入力した場合は、理解度は必須です。", errorMsg);

		// 理解度を再入力
		final WebElement understanding = webDriver.findElement(By.id("intFieldValue_0"));
		Select select = new Select(understanding);
		select.selectByVisibleText("2");
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が数値以外")
	void test07() {

		// 該当箇所までスクロール
		scrollBy("300");

		// 目標の達成度を数値以外にする
		final WebElement targetError = webDriver.findElement(By.id("content_0"));
		targetError.clear();
		targetError.sendKeys("あ");

		// 提出前のエビデンス取得
		getEvidence(new Object() {
		}, "before");

		// ページ最下部までスクロール
		scrollTo("1000");

		// 待ち処理
		visibilityTimeout(By.cssSelector("button[type='submit']"), 10);

		// ボタンを取得して押下
		final WebElement submitBtn = webDriver.findElement(By.cssSelector("button[type='submit']"));
		submitBtn.click();

		// 該当箇所までスクロール
		scrollBy("300");

		// 待ち処理
		pageLoadTimeout(50);

		// 提出後のエビデンス取得
		getEvidence(new Object() {
		}, "after");

		// エラーメッセージの一致確認
		String errorMsg = webDriver.findElement(By.className("error")).getText();
		assertEquals("* 目標の達成度は半角数字で入力してください。", errorMsg);

	}

	@Test
	@Order(8)
	@DisplayName("テスト08 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が範囲外")
	void test08() {

		// 該当箇所までスクロール
		scrollBy("300");

		// 目標の達成度を範囲外にする
		final WebElement targetError = webDriver.findElement(By.id("content_0"));
		targetError.clear();
		targetError.sendKeys("11");

		// 提出前のエビデンス取得
		getEvidence(new Object() {
		}, "before");

		// ページ最下部までスクロール
		scrollTo("1000");

		// 待ち処理
		visibilityTimeout(By.cssSelector("button[type='submit']"), 10);

		// ボタンを取得して押下
		final WebElement submitBtn = webDriver.findElement(By.cssSelector("button[type='submit']"));
		submitBtn.click();

		// 待ち処理
		visibilityTimeout(By.className("error"), 10);

		// 該当箇所までスクロール
		scrollBy("300");

		// 提出後のエビデンス取得
		getEvidence(new Object() {
		}, "after");

		// エラーメッセージの一致確認
		String errorMsg = webDriver.findElement(By.className("error")).getText();
		assertEquals("* 目標の達成度は、半角数字で、1～10の範囲内で入力してください。", errorMsg);

	}

	@Test
	@Order(9)
	@DisplayName("テスト09 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度・所感が未入力")
	void test09() {

		// 該当箇所までスクロール
		scrollBy("300");

		// 待ち処理
		visibilityTimeout(By.id("content_1"), 10);

		// 目標の達成度を未入力にする
		final WebElement targetError = webDriver.findElement(By.id("content_0"));
		targetError.clear();
		targetError.sendKeys("");

		// 所感を未入力にする
		final WebElement impressionError = webDriver.findElement(By.id("content_1"));
		impressionError.clear();
		impressionError.sendKeys("");

		// 待ち処理
		visibilityTimeout(By.id("content_1"), 10);

		// 提出前のエビデンス取得
		getEvidence(new Object() {
		}, "before");

		// ページ最下部までスクロール
		scrollTo("1000");

		// 待ち処理
		visibilityTimeout(By.cssSelector("button[type='submit']"), 10);

		// ボタンを取得して押下
		final WebElement submitBtn = webDriver.findElement(By.cssSelector("button[type='submit']"));
		submitBtn.click();

		// 待ち処理
		visibilityTimeout(By.className("error"), 10);

		// 該当箇所までスクロール
		scrollBy("300");

		// 提出後のエビデンス取得
		getEvidence(new Object() {
		}, "after");

		// エラーメッセージの取得
		List<WebElement> errorMsgs = webDriver.findElements(By.className("error"));

		// エラーメッセージの一致確認(目標の達成度)
		String targetErrorMsg = errorMsgs.get(0).getText();
		assertEquals("* 目標の達成度は半角数字で入力してください。", targetErrorMsg);

		// エラーメッセージの一致確認(所感)
		String impressionErrorMsg = errorMsgs.get(1).getText();
		assertEquals("* 所感は必須です。", impressionErrorMsg);

		// 再入力
		final WebElement learningItems = webDriver.findElement(By.id("content_0"));
		learningItems.clear();
		learningItems.sendKeys("5");
		final WebElement impression = webDriver.findElement(By.id("content_1"));
		impression.clear();
		impression.sendKeys("週報のサンプルです。");
	}

	@Test
	@Order(10)
	@DisplayName("テスト10 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：所感・一週間の振り返りが2000文字超")
	void test10() {

		// 該当箇所までスクロール
		scrollBy("350");

		// 待ち処理
		visibilityTimeout(By.id("content_1"), 10);

		// 所感を2001文字にする
		final WebElement impressionError = webDriver.findElement(By.id("content_1"));
		impressionError.clear();
		String str = "あ";
		impressionError.sendKeys(str.repeat(2001));

		// 一週間の振り返りを2001文字にする
		final WebElement retrospectiveError = webDriver.findElement(By.id("content_2"));
		retrospectiveError.clear();
		String st = "あ";
		retrospectiveError.sendKeys(st.repeat(2001));

		// 待ち処理
		visibilityTimeout(By.id("content_1"), 10);

		// 提出前のエビデンス取得
		getEvidence(new Object() {
		}, "before");

		// ページ最下部までスクロール
		scrollTo("1000");

		// 待ち処理
		visibilityTimeout(By.cssSelector("button[type='submit']"), 10);

		// ボタンを取得して押下
		final WebElement submitBtn = webDriver.findElement(By.cssSelector("button[type='submit']"));
		submitBtn.click();

		// 待ち処理
		visibilityTimeout(By.className("error"), 10);

		// 該当箇所までスクロール
		scrollBy("350");

		// 提出後のエビデンス取得
		getEvidence(new Object() {
		}, "after");

		// エラーメッセージの取得
		List<WebElement> errorMsgs = webDriver.findElements(By.className("error"));

		// エラーメッセージの一致確認(目標の達成度)
		String targetErrorMsg = errorMsgs.get(0).getText();
		assertEquals("* 所感の長さが最大値(2000)を超えています。", targetErrorMsg);

		// エラーメッセージの一致確認(所感)
		String retrospectiveErrorMsg = errorMsgs.get(1).getText();
		assertEquals("* 一週間の振り返りの長さが最大値(2000)を超えています。", retrospectiveErrorMsg);
	}

}
