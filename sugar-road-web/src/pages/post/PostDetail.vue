<template>
  <body>
    <div class="app-body">
        <div class="content">
            <div class="post-header">
                <a href="/post">◀</a>
                <h4>자유 게시판</h4>
            </div>
            <hr class="hr-blue">
                <div class="post-detail" style="min-height: 400px">
                <h3 v-text="post.title"></h3>
                    <p class="right" v-text="post.postedDate"></p>
                    <hr>
                    <p v-text="post.content"></p>
                <div class="post-image" v-for=" image in post.postImage">
                    <img :src="`http://localhost:1023${image}`" alt="img">
                </div>
                </div>
    <!--            <div class="post-comment">-->
    <!--                <p>댓글 영역</p>-->
    <!--            </div>-->
                <button is = "custom-recommendation" class = "no-border" data-referenceType="P" th:data-referenceId=${dto.postId}></button>
            <div class="post-button" v-if="isLogin===post.userId">
                <button @click="deletePost">삭제</button>
                <button @click="router.push('/post/edit/'+postId)">수정</button>
            </div>
            <link rel="stylesheet" href="/css/comment.css">
            <div is = "custom-comment" data-referenceType="post" th:data-referenceId="${dto.postId}" th:data-loginId="${session.nowLogin}" id = "comment-area"></div>
            <input type="text" class = "v-item commentInput t5" id = "commentInput" th:disabled="${session.nowLogin == null}?'disabled'" maxlength="100">
            <button class = "t6 pink2 no-border" id = "commentBtn" onclick = "writeComment()" th:disabled="${session.nowLogin == null}?'disabled'">send</button>
        </div>
    
    </div>
    </body>
  </template>
  <script setup>
    import { useRoute, useRouter } from "vue-router";
    import { onMounted, ref  } from 'vue';
    import { api } from '@/common'
    const currentRoute = useRoute();
    const router = useRouter();
    const postId = currentRoute.params.postId;
    const post = ref({});
    const isLogin = ref("");
    isLogin.value = sessionStorage.getItem("user");
    function deletePost(){
      api("http://localhost:1023/posts/"+postId, "DELETE", {})
      .then(response => {
        console.log(response);
        location.href="/post"
      })
    }
    onMounted(() => {
      console.log("id", postId)
      api("http://localhost:1023/posts/"+postId, "GET", {})
      .then(response => {
        console.log(response);
        post.value= response;
        console.log("post", post.value)
      })
    })
  </script>
  <style scoped>
  @import "../../../src/assets/post.css";
  </style>