<template>
    <div>
        <input type="text" class = "v-item commentInput t5" style = "width : 88%" id = "commentInput" v-model="commentRequest.body.content" maxlength="100">
        <button class = "t6 pink2 no-border" id = "commentBtn" @click = "createComment()">send</button>
        <CommentCard 
            v-for="(comment, index) in commentPage" 
            :data = "comment" 
            @edit-comment="editComment(url, index)"
            @delete-comment="deleteComment(index)"
        />
    </div>
</template>
<script setup>
    import {defineProps, onMounted, ref} from 'vue';
    import CommentCard from '@/components/comment/CommentCard.vue';
    import {apiService} from '@/services/APIService.js';

    const props = defineProps( {
        commentPageURL : String,
        commentType : String,
        referenceId : Number
    });
    let url = props.commentPageURL;
    const commentPage = ref([]);
    const commentRequest = ref({
        url : "",
        body : {
            content : "",
            postedDate : "",
            parentComment : null
        },
        _index : null
    });

    async function nextPage(url){
        let response = await apiService.get(url).catch(error => console.log(error));
        if ("postCommentResponseVOList" in response.data._embedded){
            commentPage.value = commentPage.value.concat(response.data._embedded.postCommentResponseVOList);
        }
        else {
            commentPage.value = commentPage.value.concat(response.data._embedded.reviewCommentResponseVOList);
        }
        sortComments();
        return response.data._links.next != undefined?response.data._links.next.href:"";
    }
    
    onMounted(
        () => {
            url = nextPage(url);
        }
    )

    function editComment(url, index){
        if (commentRequest.value._index === index){
            commentRequest.value.url = props.commentPageURL;
            commentRequest.value._index = null;
            commentRequest.value.body.content = "";
            commentRequest.value.body.parentComment = null;
        }
        else {
            commentRequest.value.url = url;
            commentRequest.value._index = index;
            commentRequest.value.body.content = commentPage.value[index].content;
            commentRequest.value.body.parentComment = commentPage.value[index].parentComment;
        }
    }

    function deleteComment(index){
        commentPage.value.splice(index, 1);
    }

    async function createComment(){
        commentRequest.value.body[props.commentType + "Id"] = props.referenceId;
        let now = new Date();
        commentRequest.value.body.postedDate = now.toISOString();
        if(commentRequest.value._index===null) {
            const newComment = await apiService.post(
                `http://localhost:1023/${props.commentType}-comment`, 
                commentRequest.value.body
            ).catch(error => console.log(error));
            commentPage.value.push(newComment.data);
            sortComments();
        }
        else {
            const newComment = await apiService.put(
                commentPage.value[commentRequest.value._index]._links.self.href, 
                commentRequest.value.body
            ).catch(error => console.log(error));
            commentPage.value[commentRequest.value._index] = newComment.data;
        }
        
        commentRequest.value.body = {
            url : "",
            body : {
                content : "",
                postedDate : "",
                parentComment : null
            },
            _index : null
        };
    }

    function sortComments(){
        commentPage.value.sort(
            (one, another) => {
                return (one.parentComment==null?one.id:one.parentComment) - (another.parentComment==null?another.id:another.parentComment);
            }
        )
    }

</script>
<style scoped>
    @import "@/assets/ui.css";
    .commentInput {
    position: sticky;
    bottom: 0;
    left : 0;
    margin : 0;
    padding : 0;
    padding-left : 5%;
    padding-right : 5%;
    height : 5vh;
    width : 60%;
    border : 1.5px solid var(--fontcolor);
    outline-color : var(--pink2);
    z-index : 10;
}

#commentBtn {
    width : 8%;
    height : 5%;
    margin : 0px;
    padding : 0px;
}

.comment {
    width : 95%;
}

.child-comment {
    width : 85%;
    margin-left : 12%;
}

.child-comment-icon {
    width : 10%;
}

.comment-content {
    height : 85vh;
}

</style>