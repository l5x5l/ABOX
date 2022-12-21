package com.strayalpaca.android.data.repositoryImpl

import com.strayalpaca.android.domain.model.OXQuizCategory
import com.strayalpaca.android.domain.model.OXQuizItem
import com.strayalpaca.android.domain.repository.OxQuizRepository
import kotlin.random.Random

class OxQuizTestRepository : OxQuizRepository {
    override suspend fun getOxQuizList(categoryIndex: Int) : List<OXQuizItem> {
        val oxQuizItemList = mutableListOf<OXQuizItem>()

        oxQuizItemList.add(OXQuizItem(index = 0, imageUrl = null, quizText = "첫번째 퀴즈입니다.", correctAnswer = true, totalAmountOfAnswer = 0, totalAmountOfCorrectAnswer = 0, categoryIndex = categoryIndex))
        oxQuizItemList.add(OXQuizItem(index = 1, imageUrl = null, quizText = "위 사진에서 주로 사용되는 색상 이름인 teal은 쇠오리에서 유래되었다..", correctAnswer = true, totalAmountOfAnswer = 0, totalAmountOfCorrectAnswer = 0, categoryIndex = categoryIndex))
        oxQuizItemList.add(OXQuizItem(index = 2, imageUrl = null, quizText = "세번째 퀴즈입니다.", correctAnswer = true, totalAmountOfAnswer = 0, totalAmountOfCorrectAnswer = 0, categoryIndex = categoryIndex))
        oxQuizItemList.add(OXQuizItem(index = 3, imageUrl = null, quizText = "'너 자신을 알라'라는 말을 한 사람은 플라톤이다.", correctAnswer = true, totalAmountOfAnswer = 0, totalAmountOfCorrectAnswer = 0, categoryIndex = categoryIndex))
        oxQuizItemList.add(OXQuizItem(index = 4, imageUrl = null, quizText = "다섯번째 퀴즈입니다.", correctAnswer = true, totalAmountOfAnswer = 0, totalAmountOfCorrectAnswer = 0, categoryIndex = categoryIndex))
        oxQuizItemList.add(OXQuizItem(index = 5, imageUrl = null, quizText = "여섯번째 퀴즈입니다.", correctAnswer = false, totalAmountOfAnswer = 0, totalAmountOfCorrectAnswer = 0, categoryIndex = categoryIndex))
        oxQuizItemList.add(OXQuizItem(index = 6, imageUrl = null, quizText = "일곱번째 퀴즈입니다.", correctAnswer = false, totalAmountOfAnswer = 0, totalAmountOfCorrectAnswer = 0, categoryIndex = categoryIndex))
        oxQuizItemList.add(OXQuizItem(index = 7, imageUrl = null, quizText = "여덟번째 퀴즈입니다.", correctAnswer = false, totalAmountOfAnswer = 0, totalAmountOfCorrectAnswer = 0, categoryIndex = categoryIndex))
        oxQuizItemList.add(OXQuizItem(index = 8, imageUrl = null, quizText = "아홉번째 퀴즈입니다.", correctAnswer = false, totalAmountOfAnswer = 0, totalAmountOfCorrectAnswer = 0, categoryIndex = categoryIndex))
        oxQuizItemList.add(OXQuizItem(index = 9, imageUrl = null, quizText = "열번째 퀴즈입니다.", correctAnswer = false, totalAmountOfAnswer = 0, totalAmountOfCorrectAnswer = 0, categoryIndex = categoryIndex))

        return oxQuizItemList
    }

    override suspend fun getOxQuizCategory(): List<OXQuizCategory> {
        val oxQuizCategoryList = mutableListOf<OXQuizCategory>()

        oxQuizCategoryList.add(OXQuizCategory(index = 0, title = "카테고리1", primaryColor = "#040C0E", updateDate = "", thumbnailUrl = "https://picsum.photos/250/500?random=0", amountOfQuiz = 10))
        oxQuizCategoryList.add(OXQuizCategory(index = 1, title = "카테고리2", primaryColor = "#000000", updateDate = "", thumbnailUrl = "https://picsum.photos/250/500?random=1", amountOfQuiz = 10))
        oxQuizCategoryList.add(OXQuizCategory(index = 2, title = "카테고리3", primaryColor = "#FE6E70", updateDate = "", thumbnailUrl = "https://picsum.photos/250/500?random=2", amountOfQuiz = 10))
        oxQuizCategoryList.add(OXQuizCategory(index = 3, title = "카테고리4", primaryColor = "#5FAEB9", updateDate = "", thumbnailUrl = "https://picsum.photos/250/500?random=3", amountOfQuiz = 10))

        return oxQuizCategoryList
    }

    override suspend fun getRandomOxQuizCategory(): OXQuizCategory {
        val randomNumber = Random.nextInt(5)
        return OXQuizCategory(index = randomNumber, title = "카테고리${randomNumber}", primaryColor = "#FE6E70", updateDate = "", thumbnailUrl = "https://picsum.photos/250/500?random=2", amountOfQuiz = 10)
    }

    override suspend fun sendSolveData(solvedQuizData : List<OXQuizItem>) {
        // DTO 로 매핑한 후 서버 전달 (서버 구현 이후 추후 작성)
        return
    }
}