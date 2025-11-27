package jp.co.sss.lms.ct.f05_exam;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.Assert.*;

import java.util.Date;
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
 * 結合テスト 試験実施機能
 * ケース13
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース13 受講生 試験の実施 結果0点")
public class Case13 {

	/** テスト07およびテスト08 試験実施日時 */
	static Date date;

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

		// 遷移先のURLの一致確認
		String url = webDriver.getCurrentUrl();
		assertEquals("http://localhost:8080/lms/course/detail", url);
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 「試験有」の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {

		// テーブルを取得
		List<WebElement> rows = webDriver.findElements(By.cssSelector(".sctionList tbody tr"));

		// 試験有を探して詳細ボタンを押下
		for (WebElement row : rows) {
			final WebElement examCell = row.findElement(By.xpath("./td[4]/span"));
			if (examCell.getText().equals("試験有")) {
				final WebElement detailBtn = row.findElement(By.xpath("./td[5]//input[@value='詳細']"));
				detailBtn.click();
				break;
			}
		}

		// 遷移後のエビデンス取得
		getEvidence(new Object() {
		});

		// 試験があるか確認
		String check = webDriver.findElement(By.xpath("//h3[2]")).getText();
		assertEquals("本日の試験", check);

		// 遷移先のURLの一致確認
		String url = webDriver.getCurrentUrl();
		assertEquals("http://localhost:8080/lms/section/detail", url);
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「本日の試験」エリアの「詳細」ボタンを押下し試験開始画面に遷移")
	void test04() {

		final WebElement detailBtn = webDriver.findElement(By.cssSelector("input[value='詳細']"));
		detailBtn.click();

		// 遷移後のエビデンス取得
		getEvidence(new Object() {
		});

		// 遷移先のURLの一致確認
		String url = webDriver.getCurrentUrl();
		assertEquals("http://localhost:8080/lms/exam/start", url);
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 「試験を開始する」ボタンを押下し試験問題画面に遷移")
	void test05() {

		// ページ最下部までスクロール
		scrollTo("2000");

		// 試験開始前の過去の試験結果のエビデンス取得
		getEvidence(new Object() {
		}, "pastTestResultsBfore");

		// ページ最上部までスクロール
		scrollTo("10");

		// 遷移前のエビデンス取得
		getEvidence(new Object() {
		}, "before");

		final WebElement startBtn = webDriver.findElement(By.cssSelector("input[value='試験を開始する']"));
		startBtn.click();

		// 遷移後のエビデンス取得
		getEvidence(new Object() {
		}, "after");

		// 遷移先のURLの一致確認
		String url = webDriver.getCurrentUrl();
		assertEquals("http://localhost:8080/lms/exam/question", url);
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 未回答の状態で「確認画面へ進む」ボタンを押下し試験回答確認画面に遷移")
	void test06() {

		// ページ最下部までスクロール
		scrollTo("5000");

		// 遷移前のエビデンス取得
		getEvidence(new Object() {
		}, "before");

		final WebElement checkBtn = webDriver.findElement(By.cssSelector("input[value='確認画面へ進む']"));
		checkBtn.click();

		// 遷移後のエビデンス取得
		getEvidence(new Object() {
		});

		// 遷移先のURLの一致確認
		String url = webDriver.getCurrentUrl();
		assertEquals("http://localhost:8080/lms/exam/answerCheck", url);
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 「回答を送信する」ボタンを押下し試験結果画面に遷移")
	void test07() throws InterruptedException {

		// ページ最下部までスクロール
		scrollTo("5000");

		// 遷移前のエビデンス取得
		getEvidence(new Object() {
		}, "before");

		final WebElement sendBtn = webDriver.findElement(By.cssSelector("button[id='sendButton']"));
		sendBtn.click();

		acceptAlertIfPresent(webDriver);

		// 遷移後のエビデンス取得
		getEvidence(new Object() {
		}, "after");

		// 遷移先のURLの一致確認
		String url = webDriver.getCurrentUrl();
		assertEquals("http://localhost:8080/lms/exam/result", url);
	}

	@Test
	@Order(8)
	@DisplayName("テスト08 「戻る」ボタンを押下し試験開始画面に遷移後当該試験の結果が反映される")
	void test08() {

		// ページ最下部までスクロール
		scrollTo("5000");

		// 遷移前のエビデンス取得
		getEvidence(new Object() {
		}, "before");

		final WebElement backBtn = webDriver.findElement(By.cssSelector("input[value='戻る']"));
		backBtn.click();

		// 遷移後のエビデンス取得
		getEvidence(new Object() {
		}, "after");

		// 遷移先のURLの一致確認
		String url = webDriver.getCurrentUrl();
		assertEquals("http://localhost:8080/lms/exam/start", url);

		// 試験後の過去の試験結果のエビデンス取得
		getEvidence(new Object() {
		}, "pastTestResultsAfter");

		// 結果が反映されているかの確認
		List<WebElement> pastTestResutts = webDriver.findElements(By.xpath("//*[@id=\"main\"]/div/table[2]//td"));
		boolean found = false;
		for (WebElement td : pastTestResutts) {
			if (td.getText().equals("4回目")) {
				found = true;
				break;
			}
		}
		assertTrue(found);

	}

}
