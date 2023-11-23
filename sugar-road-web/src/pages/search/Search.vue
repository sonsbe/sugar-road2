<template>
<div class="search-container">
<div class="app-body">
    <div class="header center"><img src="src/assets/users/img/Sugar-Road Logo.png"></div>
    <div class="content">
        <div class="search-bar">
        <form @submit.prevent="search">
          <font-awesome-icon icon="fa-solid fa-magnifying-glass" style="color: #8b929c;" />
            <input type="text" class="input" v-model="query" placeholder="찾고 싶은 가게 또는 디저트를 입력하세요" required>
        </form>
        </div>
    <hr>
<div class="post-result" v-if="resultList.postList">
  <h6>게시글</h6>
    <SearchPostCard v-for="(post, index) in resultList.postList" :post="post" :key="index"></SearchPostCard>
</div>
<div class="review-result" v-if="resultList.reviewList">
    <h6>리뷰</h6>
    <SearchReviewCard v-for="(review, index) in resultList.reviewList" :review="review" :key="index"></SearchReviewCard>
</div>
<div class="store-result" v-if="resultList.storeList">
    <h6 class="bold">가게</h6>
    <SearchStoreCard v-for="(store, index) in resultList.storeList" :store="store" :key="index"></SearchStoreCard>
</div>
    </div>
</div>
</div>
</template>

<script setup>
import { ref } from 'vue';
import { api } from '@/common'
import SearchPostCard from '../../components/search/SearchPostCard.vue';
import SearchReviewCard from '../../components/search/SearchReviewCard.vue';
import SearchStoreCard from '../../components/search/SearchStoreCard.vue';
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