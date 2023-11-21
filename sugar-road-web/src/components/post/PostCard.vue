<template>
  <div class="post-thumbnail">
    <router-link :to="'/post/'+p.post.id" :id="p.post.id">
        <table class="v-table">
            <tr>
                <td>
                    <p class="t5 bold" v-text="p.post.title"></p>
                </td>
                <td class="v-table-half right">
                    <p class="t6" v-text="`조회수: ${p.post.viewsCount}`"></p>
                </td>

            </tr>
            <tr class="center">
                <td colspan="2">

                    <img :src="`http://localhost:1023${p.post.postImage.length>0 ? p.post.postImage[0] : '/images/93c339e1-eb4d-4d39-b773-bd50913b7dc1thymeleaf-logo-6E4D42A713-seeklogo.com.png'}`" alt="img">
                </td>
            </tr>
            <tr>
                <td colspan="2" class="left">
                    <span class="t5 bold" v-text="p.post.userId"></span>
                    <span class="t5" v-text="p.post.content"></span>
                </td>
            </tr>
        </table>
    </router-link>
    </div>
</template>
<script setup>

import { defineProps } from 'vue';

  // const post = defineProps({
  //   title: String,
  //   commentCount: Number,
  //   recommendCount: Number,
  //   postImage: Array,
  //   userId: String,
  //   content: String
  // })
  const p = defineProps({
    post:{
      type: Object
    }
  })
  console.log("post", p.post);
</script>