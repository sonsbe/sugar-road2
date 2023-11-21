<template>
    ????
    <cards reviewPageURL = "http://localhost:1023/review/of/store/108"></cards>
    <router-link to="/review/write/store/108">리뷰 작성 테스트</router-link>
</template>
<script setup>
    import Cards from '@/components/review/Cards.vue';
</script>