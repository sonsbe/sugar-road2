<template>
    <div class="bottom_menu t4">
        <div>
            <router-link to="/">
                <font-awesome-icon
                icon="fa-solid fa-house t-gray"
                style="{color: '#d3d3d3'};"
              />
            </router-link>
        </div>
        <div>
            <router-link to="/search">
                <font-awesome-icon icon="magnifying-glass" style="color: #d3d3d3;" />
            </router-link>
        </div>
        <div>
            <router-link to="/post">
                <font-awesome-icon
                icon="fa-solid fa-comments"
                style="color: #d3d3d3;"
              />
            </router-link>
        </div>
        <div>
            <router-link to="/store">
                <font-awesome-icon
                icon="fa-solid fa-store"
                style="color: #d3d3d3;"
              />
            </router-link>
        </div>
        <div>
            <router-link to="/mypage">
                <font-awesome-icon
                icon="fa-solid fa-user"
                style="color: #d3d3d3;"
              />
        </router-link>
        </div>
    </div>
</template>
<script setup>
</script>
<style>
</style>