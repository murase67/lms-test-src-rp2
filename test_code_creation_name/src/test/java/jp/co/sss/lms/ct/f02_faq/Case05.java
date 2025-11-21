package jp.co.sss.lms.ct.f02_faq;

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
 * 結合テスト よくある質問機能
 * ケース05
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース05 キーワード検索 正常系")
public class Case05 {

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
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {
		// 上部メニューの機能リンクを取得し押下
		final WebElement menu = webDriver.findElement(By.className("dropdown-toggle"));
		menu.click();

		// 上部メニューのエビデンス取得
		getEvidence(new Object() {
		}, "menu");

		// メニューの中のヘルプリンクを取得し押下
		final WebElement helpLink = webDriver.findElement(By.linkText("ヘルプ"));
		helpLink.click();

		// ヘルプ画面のエビデンス取得
		getEvidence(new Object() {
		}, "help");

		// 遷移先URLの一致確認
		String url = webDriver.getCurrentUrl();
		assertEquals("http://localhost:8080/lms/help", url);
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {
		// URLを取得し押下
		final WebElement questionLink = webDriver.findElement(By.linkText("よくある質問"));
		questionLink.click();

		// 別タブで開く
		Object[] windowHandles = webDriver.getWindowHandles().toArray();
		webDriver.switchTo().window((String) windowHandles[1]);

		// 遷移後のエビデンス取得
		getEvidence(new Object() {
		});

		// 遷移先URLの一致確認
		String url = webDriver.getCurrentUrl();
		assertEquals("http://localhost:8080/lms/faq", url);
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 キーワード検索で該当キーワードを含む検索結果だけ表示")
	void test05() {
		// 値の取得
		final WebElement keyWord = webDriver.findElement(By.id("form"));
		final WebElement searchBtn = webDriver.findElement(By.cssSelector("input[value='検索']"));

		// キー入力
		keyWord.clear();
		keyWord.sendKeys("途中退校");

		// 検索前のエビデンス取得
		getEvidence(new Object() {
		}, "before");

		// 検索ボタン押下
		searchBtn.click();

		// ページ最下部までスクロール
		scrollTo("1000");

		// 検索結果のエビデンス取得
		getEvidence(new Object() {
		}, "after");

		// ページ最上部までスクロール
		scrollTo("10");

		// 検索結果にキーワードが含まれているかの確認
		List<WebElement> results = webDriver.findElements(By.cssSelector("tbody tr"));
		assertTrue(results.size() > 0);
		for(WebElement result : results) {
			String text = result.getText();
			assertTrue(text.contains("途中退校"));
		}
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 「クリア」ボタン押下で入力したキーワードを消去")
	void test06() {
		// 情報を取得
		final WebElement clearBtn = webDriver.findElement(By.cssSelector("input[value='クリア']"));
		final WebElement form = webDriver.findElement(By.className("form-control"));

		// ボタン押下前のエビデンス取得
		getEvidence(new Object() {
		}, "before");

		// クリアボタンを押下
		clearBtn.click();

		// ボタン押下後のエビデンス取得
		getEvidence(new Object() {
		}, "after");

		// キーワード欄が消去されていることを確認
		assertEquals(form.getText(), "");
	}

}
