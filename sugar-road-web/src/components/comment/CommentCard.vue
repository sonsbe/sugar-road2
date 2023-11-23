<template>
    <table :class="[data.parentComment!=null?'child-comment':'comment', 'v-table', 'left']" >
        <tr>
            <td class = "v-table-half t5">
                {{data.nickname}}
            </td>
            <td class = ".v-table-eight">
            </td>
            <td class = ".v-table-eight">
            </td>
            <td class = "v-table-quater t6 right">
                {{data.postedDate.substring(0,10)}}
            </td>
        </tr>
        <tr>
            <td colspan="4" class = "t6 v-item">
                {{data.content}}
            </td>
        </tr>
        <tr>
            <td>
                <Suspense>
                    <Recommendation :data = "data._links"></Recommendation>
                </Suspense>
            </td>
            <td class = "t6">
                <a v-if="data.parentComment==undefined&&userId!=null" onclick = 'setParent("+ comment[this.referenceType + "CommentId"] +");' class = 't-lightgray'>답글</a>
            </td>
            <td class = "t6">
                <a v-if="data.userId === userId" @click="editComment(data._links.self.href)" class = 't-lightgray'>수정</a>
            </td>
            <td class = "t6">
                <a v-if="data.userId === userId" @click="deleteComment(data._links.self.href)" class = 't-lightgray'>삭제</a>
            </td>
        </tr>
    </table>
</template>
<script setup>
    import {defineProps, defineEmits} from 'vue';
    import Recommendation from '@/components/recommendation/Recommendation.vue';
    import {apiService} from '@/services/APIService.js';

    const props = defineProps( {
        data : Object
    });
    const emit = defineEmits(["edit-comment", "delete-comment"]);
    const userId = sessionStorage.getItem("user");
    function editComment(url){
        emit("edit-comment", url);
    }
    function deleteComment(url){
        apiService.delete(url)
            .then(response => {
                if (response.status === 200){
                    emit("delete-comment");
                }
            });
    }
</script>
<style scoped>
@import "@/assets/comment.css";
@import "@/assets/ui.css";
</style>