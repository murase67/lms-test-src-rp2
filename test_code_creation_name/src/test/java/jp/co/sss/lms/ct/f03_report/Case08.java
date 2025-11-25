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

import jp.co.sss.lms.ct.util.WebDriverUtils;

/**
 * 結合テスト レポート機能
 * ケース08
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース08 受講生 レポート修正(週報) 正常系")
public class Case08 {

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
	@DisplayName("テスト03 提出済の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {

		// 全てのtrを取得
		List<WebElement> rows = webDriver.findElements(By.cssSelector("tbody tr"));

		// 対象の日付を格納
		String targetDate = "2022年10月2日(日)";

		// 対象の詳細ボタンを探して押下
		for (WebElement row : rows) {
			String dateText = row.findElement(By.cssSelector("td:nth-child(1)")).getText();
			if (dateText.equals(targetDate)) {
				row.findElement(By.cssSelector("input[value='詳細']")).click();
				break;
			}
		}

		// 遷移後のエビデンス取得
		getEvidence(new Object() {
		});

		// 遷移先のページの日付が対象と一致しているか確認
		String date = webDriver.findElement(By.cssSelector("#sectionDetail > h2 > small")).getText();
		assertEquals("2022年10月2日", date);
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「確認する」ボタンを押下しレポート登録画面に遷移")
	void test04() {

		// ページ最下部までスクロール
		scrollTo("1000");

		// 遷移前のエビデンス取得
		getEvidence(new Object() {
		}, "before");

		// 提出済み週報【デモ】を確認するボタンを取得し押下
		final WebElement submissionBtn = webDriver.findElement(By.cssSelector("input[value='提出済み週報【デモ】を確認する']"));
		submissionBtn.click();

		// 遷移後のエビデンス取得
		getEvidence(new Object() {
		}, "after");

		// 遷移先URLの一致確認
		String url = webDriver.getCurrentUrl();
		assertEquals("http://localhost:8080/lms/report/regist", url);
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しセクション詳細画面に遷移")
	void test05() {

		// ページ最下部までスクロール
		scrollTo("1000");

		// 情報の取得
		final WebElement inputText = webDriver.findElement(By.id("content_1"));
		final WebElement submitBtn = webDriver
				.findElement(By.cssSelector("#main > form > div:nth-child(3) > fieldset > div > div > button"));

		// 入力キー
		inputText.clear();
		inputText.sendKeys("修正サンプル");

		// 提出ボタン押下前のエビデンス取得
		getEvidence(new Object() {
		}, "before");

		// 提出ボタン押下
		submitBtn.click();

		// 提出ボタン押下後のエビデンス取得
		getEvidence(new Object() {
		}, "after");

		String url = webDriver.getCurrentUrl();
		assertEquals("http://localhost:8080/lms/section/detail?sectionId=2", url);
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test06() {

		// リンクを取得し、押下する
		final WebElement userLink = webDriver
				.findElement(By.cssSelector("#nav-content > ul.nav.navbar-nav.navbar-right > li:nth-child(2) > a"));
		userLink.click();

		// 遷移後のエビデンス取得
		getEvidence(new Object() {
		});

		// 遷移後URLの一致確認
		String url = webDriver.getCurrentUrl();
		assertEquals("http://localhost:8080/lms/user/detail", url);
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 該当レポートの「詳細」ボタンを押下しレポート詳細画面で修正内容が反映される")
	void test07() {

		// ページ最下部までスクロール
		scrollTo("1000");

		// ボタン押下前のエビデンス取得
		getEvidence(new Object() {
		}, "before");

		// 対象の詳細ボタン押下
		final WebElement targetRepo = webDriver.findElement(By.cssSelector("#main > table:nth-child(8) > tbody > tr:nth-child(3) > td:nth-child(5) > form:nth-child(1) > input.btn.btn-default"));
		targetRepo.click();

		// ボタン押下後のエビデンス取得
		getEvidence(new Object() {
		}, "after");

		// 遷移後URLの一致確認
		String url = webDriver.getCurrentUrl();
		assertEquals("http://localhost:8080/lms/report/detail", url);

		// 修正内容の一致確認
		String repoText = webDriver.findElement(By.cssSelector("#main > div:nth-child(4) > table > tbody > tr:nth-child(2) > td")).getText();
		assertEquals("修正サンプル", repoText);
	}

}
