<template>
<div class="search-container">
<div class="app-body">
    <div class="header center"><img src="/images/icons/sugarroad.png"></div>
    <div class="content">
        <div class="search-bar">
        <form @submit.prevent="search">
          <font-awesome-icon icon="fa-solid fa-magnifying-glass" style="color: #8b929c;" />
            <input type="text" class="input" v-model="query" placeholder="찾고 싶은 가게 또는 디저트를 입력하세요" required>
        </form>
        </div>

    <hr>
<div class="post-result" v-if="`${resultList.postList}`">
  <h6>게시글</h6>
    <SearchCard v-for="(post, index) in resultList.postList" :object="post" :key="index"></SearchCard>
</div>
<div class="review-result" th:if="${reviewList}">
    <h6>리뷰</h6>
    <div class="thumbnail skyblue" th:each="review : ${reviewList}">
        <a th:href="@{/review/detail(id=${review.reviewId})}">
            <table class="v-table">
                <tr>
                    <td>
                        <p class="t5 bold" th:text="${review.storeId}"></p>
                    </td>
                    <td class="v-table-half right">
                        <p class="t6">댓글 수: 3</p>
                    </td>
                </tr>
                <tr>
                    <td>
                        <p th:text="${review.content}"></p>
                    </td>
                </tr>
                <tr>
                    <td colspan="2" class="right">
                        <th:block th:each="num :${#numbers.sequence(0, review.star-1)}">
                            <span>★</span>
                        </th:block>
                    </td>
                </tr>
            </table>
        </a>
    </div>
</div>
<div class="store-result" th:if="${storeList}">
    <h6 class="bold">가게</h6>
    <div class="thumbnail lightgray" th:each="store : ${storeList}">
        <a th:href="@{/store/detail(storeId=${store.storeId})}">
            <table class="v-table">
                <tr>
                    <td class="left">
                        <p class="t5 bold" th:text="${store.storeName}"></p>
                    </td>
                </tr>
                <tr>
                    <td>
                        <img th:src="${store.storeImagePath}? ${store.storeImagePath} : '/images/icons/bread.png'" alt="">
                    </td>
                </tr>
                <tr>
                    <td class="right">
                        <p th:text="${store.phoneNumber}"></p>
                    </td>
                </tr>
            </table>
        </a>
    </div>
</div>
<h3 th:if="${noResult}" th:text="${noResult}"></h3>
    </div>
</div>
</div>
</template>

<script setup>
import { ref } from 'vue';
import { api } from '@/common'
import SearchCard from '../../components/search/SearchCard.vue';

const query = ref("");
const resultList = ref({});
function search(){
  console.log("검색", query.value)
  api("http://localhost:1023/search?query="+query.value, "GET", {})
  .then(response => {
    console.log("response", response);
    resultList.value = response;
  })
  console.log("list", resultList.value);
}
</script>

<style lang="scss">
@import "../../../src/assets/search.css";
</style>