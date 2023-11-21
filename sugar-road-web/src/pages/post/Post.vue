<template>
  <body>
  <div class="post-index-container">
  <div class="app-body">
      <div class="content">
          <h3>Open Forum</h3>
  <div class="category">
    <button @click="filtCategory('01')">🍩빵리뷰</button>
    <button @click="filtCategory('02')">🚲일상</button>
    <button @click="filtCategory('03')">📝정보</button>

  </div>
          <div class="search">
          <form @submit.prevent="search">
            <font-awesome-icon icon="fa-solid fa-magnifying-glass" style="color: #8b929c;" />
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

  <a href="/post/write">
  <div class="post-button-index bold h5">
          글 작성
        </div>
      </a>
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
    const col = ref("");
    const category = ref("");

    function search(){
      console.log(query.value)
      api("http://localhost:1023/posts?query="+query.value, "GET", {})
      .then(response => {
        console.log("response", response);
        postList.value = response;
        console.log(postList)
      })
    }

    function filtCategory(id){
      category.value=id;
      console.log(category.value)
      api("http://localhost:1023/posts?category="+category.value, "GET", {})
      .then(response => {
        postList.value = response;
      })
    }

    function sort(){

      var uri;
      if(category.value.length>0)
        uri = "http://localhost:1023/posts?col="+col.value+"&category="+category.value;
      else
        uri = "http://localhost:1023/posts?col="+col.value;
      console.log("col", col.value, "uri", uri)
      api(uri, "GET", {})
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
  
  <style lang="scss">
  @import "../../../src/assets/post.css"
  </style>