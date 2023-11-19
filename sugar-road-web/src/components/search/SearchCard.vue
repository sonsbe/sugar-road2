<template>
    <div class="thumbnail pink1">
        <router-link :to="'/post/'+p.object.id" :id="p.object.id">
        <table class="v-table">
            <tr>
                <td>
                    <p class="t5 bold" v-text="p.object.title"></p>
                </td>
                <td class="v-table-half right">
                    <p class="t6" v-text="`댓글수:${p.object.commentCount} 조회수:${p.object.viewsCount}`"></p>
                </td>

            </tr>
            <tr>
                <td>
                <p v-text="p.object.content"></p>
                </td>
            </tr>
        </table>
        </router-link>
    </div>
</template>

<script setup>
import { ref } from 'vue';
const p = defineProps({
  kind:{
    type: String
  },
    object:{
      type: Object
    }
  })
</script>
