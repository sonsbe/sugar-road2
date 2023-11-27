<template>
<div class="app-body">
    <div class="content">
        <h3>작성한 게시글 목록</h3>
        <hr>
        <div class="post-list" th:if="${postList}">
            <div v-for="(post, index) in postList " class="thumbnail">
              <router-link :to="'/post/'+post.id" :id="post.id">
                    <table class="v-table">
                        <div v-if="post.postImage.length > 0">
                            <tr>
                                <td class="v-table-third" rowspan="2">
                                  <img :src="`http://localhost:1023${post.postImage.length>0 ? post.postImage[0] : '/images/93c339e1-eb4d-4d39-b773-bd50913b7dc1thymeleaf-logo-6E4D42A713-seeklogo.com.png'}`" alt="img">
                                </td>
                                <td style="vertical-align: top;">
                                    <div class="bold t4">
                                        {{ post.title }}
                                    </div>
                                    <div>
                                        {{ post.content }}
                                    </div>
                                </td>
                            </tr>
                          </div>
                        <div v-else>
                            <tr>
                                <td class="bold" v-text="post.title"></td>
                            </tr>
                            <tr>
                                <td v-text="post.content"></td>
                            </tr>
                        </div>
                    </table>
                </router-link>
                <hr>
            </div>

        </div>
        </div>
        </div>
</template>
<script setup>
import { onMounted, ref } from "vue";
import { api } from "@/common";
const postList = ref([]);
const userId = sessionStorage.getItem("user");
onMounted(() => {
  api("http://localhost:1023/posts/user/"+userId, "GET", {}).then((response) => {
    console.log("MyPost", response);
    postList.value = response;
  });
});
</script>
<style scoped>
@import "@/assets/post.css";
td > img{
    width: 100%;
    height: 100px;
    object-fit: cover;
}
</style>