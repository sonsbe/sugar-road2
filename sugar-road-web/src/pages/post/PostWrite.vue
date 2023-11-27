<template>
    <div class="app-body">
      <div class="content">
        <a href="/post">◀</a>
        <div class="header" style="text-align: center">
          
          <p v-if="uri.includes('edit')">글 수정</p>
          <p v-else>글 작성</p>
        </div>
        <hr />
        <div class="post-content">

          <form>
            <input type="hidden" v-model="postId" />
            <input
              type="text"
              style="width: 100%; border: none"
              placeholder="제목"
              v-model="postRequest.title"
            /><br />
            <hr />
            <textarea
              name="content"
              rows="20"
              style="width: 100%; border: none"
              placeholder="내용"
              v-model="postRequest.content"
            ></textarea
            ><br />
            <hr />
            <input type="hidden" name="userId" :value="post.userId" /><br />

            <!--<input
              type="text"
              name="postCategoryId"
              v-model="postRequest.postCategoryId"
            /><br /> -->
            카테고리:
            <select v-model="postRequest.postCategoryId">
              <option value="01">
                빵리뷰
              </option>
              <option  value="02">
                일상
              </option>
              <option  value="03">
                정보
              </option>
            </select>
            <br>
            <input
              type="file"
              class="form-control"
              ref="uploadImages"
              @change="fileSelect"
              multiple
            /><br />

            <div v-if="post.postImage?.length > 0">
              삭제할 이미지:
              <div v-for="image in post.postImage">
                <label>
                  <input
                    type="checkbox"
                    name="postImage"
                    :value="image"
                    v-model="postRequest.postImage"
                  />
                  <img :src="`http://localhost:1023${image}`" alt="img" width="50px"/>
                </label>
              </div>
            </div>
          </form>
          <div class="post-button">
            <button onclick="history.back(-1)">취소</button>
            <button @click="postWrite">작성</button>
          </div>
        </div>
      </div>
    </div>
</template>
<script setup>
import { useRoute, useRouter } from "vue-router";
import { onMounted, ref } from "vue";
import { api } from "@/common";
import axios from "axios";
const currentRoute = useRoute();
const router = useRouter();
const postId = currentRoute.params.postId;
let uri = window.location.pathname;
console.log(uri);
const post = ref({});

const postRequest = ref({
  title: "",
  content: "",
  postImage: [],
  userId: "",
  postCategoryId: "",
});
const uploadImages = ref([]);

function fileSelect(event) {
  console.log(event.target.files);
  uploadImages.value = event.target.files;
}

function postWrite() {
  console.log(postRequest);
  const formData = new FormData();

  const jsonData = {
    title: postRequest.value.title,
    content: postRequest.value.content,
    postImage: postRequest.value.postImage,
    userId: sessionStorage.getItem("user"),
    postCategoryId: postRequest.value.postCategoryId,
  };
  const json = JSON.stringify(jsonData);
  const blob = new Blob([json], { type: "application/json" });
  formData.append("postRequest", blob);

  if (uploadImages.value.length > 0) {
    console.log("length", uploadImages.value.length==null)
    for (const file of uploadImages.value) {
      formData.append("uploadImages", file);
    }
  }
  console.log(formData.get("postRequest"));

  if (uri.includes("edit")) {
    axios
      .put("http://localhost:1023/posts/" + postId, formData, {
        headers: {
          "Content-Type": "multipart/form-data",
          "Authorization" : sessionStorage.getItem("token")
        },
      })
      .then((response) => {
        console.log(response.data);
        router.push("/post/" + postId);
      });
  } else {
    axios
      .post("http://localhost:1023/posts", formData, {
        headers: {
          "Content-Type": "multipart/form-data",
          "Authorization": sessionStorage.getItem("token")
        },
      })
      .then((response) => {
        console.log(response.data);
        router.push("/post");
      });
  }
}

onMounted(() => {
  console.log("token", sessionStorage.getItem("token"))
  if (uri.includes("edit")) {
    console.log("id", postId);
   
    api("http://localhost:1023/posts/" + postId, "GET", {})
    .then((response) => {
      console.log(response);
      post.value = response;
      postRequest.value.title = response.title;
      postRequest.value.content = response.content;
      //postRequest.value.userId = response.userId;
      postRequest.value.postCategoryId = response.postCategoryId;
      console.log("postRequest", postRequest);
    });
  }
}

);
</script>

<style scoped>
@import "../../../src/assets/post.css";
</style>
