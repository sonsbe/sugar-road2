<template>
  <body>
  <div class="post-index-container">
  <div class="app-body">
      <div class="content">
          <h3>Open Forum</h3>
  <div class="category">
      <span><a href="/post?category=01">🍩빵리뷰</a></span>
      <span> <a href="/post?category=02">🚲일상</a></span>
      <span><a href="/post?category=03">📝정보</a></span>
  </div>
          <div class="search">
          <form @submit.prevent="search">
              <i class="fa-solid fa-magnifying-glass" style="color: #d2d7e0;"></i>
              <input type="text" v-model="query" placeholder="검색">
          </form>
          </div>
          <div class="right">
          <label for="sort">
          </label>
          <form>
              <select class="form-select" v-model="col" @change="sort" style="border: none">
                  <option value="" disabled selected>정렬</option>
                  <option value="recommendation">인기순</option>
                  <option value="postedDate">최신순</option>
              </select>
          </form>
          </div>
  <div class="post-list">
      <PostCard v-for="(post, index) in postList" :post="post" :key="index"></PostCard>
  </div>
  <!-- <div class="post-button-index">
     <button onclick="location.href='/post/write'"><span class="t6">작성</span></button>
    
  </div> -->
          <a class="post-button-index bold h5" href="/post/write">글 작성</a>
      </div>
  </div>
  </div>
  </body>
  </template>
  
  <script setup>
    import { onMounted, ref } from 'vue';
    import { api } from '@/common'
    import PostCard from '../../components/post/PostCard.vue';
    
    const postList = ref([]);
    const query = ref("");
    const col = ref("")
    function search(){
      console.log(query.value)
      api("http://localhost:1023/posts?query="+query.value, "GET", {})
      .then(response => {
        console.log("response", response);
        postList.value = response;
        console.log(postList)
      })
    }
    function sort(){
      console.log("col", col.value)
      api("http://localhost:1023/posts?col="+col.value, "GET", {})
      .then(response => {
        console.log("response", response);
        postList.value = response;
        console.log(postList)
      })
    }
  
    onMounted(() => {
      api("http://localhost:1023/posts", "GET", {})
      .then(response => {
        console.log(response);
        postList.value = response;
        console.log(postList)
      })
    })
  </script>
  
  <style lang="scss" scoped>
  @import "../../../src/assets/post.css"
  </style>