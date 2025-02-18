package lotto.domain

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.collections.shouldHaveSize

class LottoTest : StringSpec({
    "로또는 6자리 숫자로 이루어져 있다." {
        val lottoNumbers = setOf(
            LottoNumber.from(1),
            LottoNumber.from(2),
            LottoNumber.from(3),
            LottoNumber.from(4),
            LottoNumber.from(5),
            LottoNumber.from(6)
        )

        Lotto(lottoNumbers).lottoNumbers shouldHaveSize 6
    }

    "로또 6자리 수로 이루어지지 않는다면 예외가 발생한다." {
        val lottoNumbers = setOf(LottoNumber.from(1), LottoNumber.from(2))

        shouldThrow<IllegalArgumentException> {
            Lotto(lottoNumbers)
        }
    }

    val winningLotto = Lotto(
        setOf(
            LottoNumber.from(1),
            LottoNumber.from(2),
            LottoNumber.from(3),
            LottoNumber.from(4),
            LottoNumber.from(5),
            LottoNumber.from(6)
        )
    )

    val lotto1 = Lotto(
        setOf(
            LottoNumber.from(4),
            LottoNumber.from(5),
            LottoNumber.from(6),
            LottoNumber.from(7),
            LottoNumber.from(8),
            LottoNumber.from(9)
        )
    )

    val lotto2 = Lotto(
        setOf(
            LottoNumber.from(1),
            LottoNumber.from(2),
            LottoNumber.from(7),
            LottoNumber.from(8),
            LottoNumber.from(9),
            LottoNumber.from(10)
        )
    )

    "두개의 로또의 당첨갯수를 구한다." {
        forAll(
            row(lotto1, 3),
            row(lotto2, 2),
        ) { lotto, resultCount ->
            winningLotto.match(lotto) shouldHaveSize resultCount
        }
    }

    "보너스를 추가한 로또의 당첨갯수를 구한다." {
        val bonusLottoNumber = LottoNumber.from(7)

        forAll(
            row(lotto1, 4),
            row(lotto2, 3),
        ) { lotto, resultCount ->
            winningLotto.match(lotto, bonusLottoNumber) shouldHaveSize resultCount
        }
    }
})
