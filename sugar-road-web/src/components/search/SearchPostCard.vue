<template>
    <div class="thumbnail pink1">
        <router-link :to="'/post/'+p.post.id" :id="p.post.id">
        <table class="v-table">
            <tr>
                <td>
                    <p class="t5 bold" v-text="p.post.title"></p>
                </td>
                <td class="v-table-half right">
                    <p class="t6" v-text="`댓글수:${p.post.commentCount} 조회수:${p.post.viewsCount}`"></p>
                </td>

            </tr>
            <tr>
                <td>
                <p v-text="p.post.content"></p>
                </td>
            </tr>
        </table>
        </router-link>
    </div>
      
</template>

<script setup>
import { ref } from 'vue';
const p = defineProps({
    post:{
      type: Object
    }
  })
</script>
