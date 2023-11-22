<template>
  <body>
    <div class="post-index-container">
      <div class="app-body">
        <div class="content">
          <h3>Open Forum</h3>
          <div class="category">
            <button :class="{ clicked : isClicked[0] }" @click="[filtCategory('01'), clickButton(0)]">🍩빵리뷰</button>
            <button :class="{ clicked : isClicked[1] }" @click="[filtCategory('02'), , clickButton(1)]">🚲일상</button>
            <button :class="{ clicked : isClicked[2] }" @click="[filtCategory('03'), clickButton(2)]">📝정보</button>
          </div>
          <div class="search">
            <form @submit.prevent="search">
              <font-awesome-icon
                icon="fa-solid fa-magnifying-glass"
                style="color: #8b929c"
              />
              <input type="text" v-model="query" placeholder="검색" />
            </form>
          </div>
          <div class="right">
            <label for="sort"> </label>
            <form>
              <select
                class="form-select"
                v-model="col"
                @change="sort"
                style="border: none"
              >
                <option value="" disabled selected>정렬</option>
                <option value="recommendCount">인기순</option>
                <option value="postedDate">최신순</option>
              </select>
            </form>
          </div>
          <div class="post-list">
            <PostCard
              v-for="(post, index) in postList"
              :post="post"
              :key="index"
            ></PostCard>
          </div>
          <!-- <div class="post-button-index">
     <button onclick="location.href='/post/write'"><span class="t6">작성</span></button>
    
  </div> -->

          <a 
          v-if="isLogin"
          href="/post/write">
            <div
           
              class="post-button-index bold h5"
            >
              글 작성
            </div>
          </a>

        </div>
      </div>
    </div>
  </body>
</template>

<script setup>
import { onMounted, ref } from "vue";
import { api } from "@/common";
import PostCard from "../../components/post/PostCard.vue";
const postList = ref([]);
const query = ref("");
const col = ref("");
const category = ref("");
const isLogin = ref();
const isClicked = ref([false, false ,false]);

isLogin.value = sessionStorage.getItem("user");

console.log(sessionStorage.getItem("user"));
console.log("session", isLogin.value!==null);
function search() {
  console.log(query.value);
  api("http://localhost:1023/posts?query=" + query.value, "GET", {}).then(
    (response) => {
      postList.value = response;
    }
  );
}

function clickButton(index){
  isClicked.value = [false, false, false];
  isClicked.value[index] = !isClicked.value[index];
  console.log("isClicked", isClicked);
}

function filtCategory(id) {
  category.value = id;
  console.log(category.value);
  api("http://localhost:1023/posts?category=" + category.value, "GET", {}).then(
    (response) => {
      postList.value = response;
    }
  );
}

function sort() {
  var uri;
  if (category.value.length > 0)
    uri =
      "http://localhost:1023/posts?col=" +
      col.value +
      "&category=" +
      category.value;
  else uri = "http://localhost:1023/posts?col=" + col.value;
  console.log("col", col.value, "uri", uri);
  api(uri, "GET", {}).then((response) => {
    postList.value = response;
  });
}

onMounted(() => {
  api("http://localhost:1023/posts", "GET", {}).then((response) => {
    postList.value = response;
  });
});
</script>

<style lang="scss">
@import "../../../src/assets/post.css";
.clicked{
        border: 1px solid rgb(228, 228, 228);
        border-radius: 5px;
        padding: 1.5vh 4vh 1.5vh 3vh;
        box-shadow: rgba(0, 0, 0, 0.15) 1.95px 1.95px 2.6px;
        margin: 5px;
        background-color: skyblue;
        cursor: pointer;
}
</style>
