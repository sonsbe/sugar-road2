import { createRouter, createWebHistory } from "vue-router";
import Home from "@/pages/main/Home.vue";
import Search from "@/pages/search/Search.vue";
import Post from "@/pages/post/Post.vue";
import Store from "@/pages/store/Store.vue";
import StoreWrite from "@/pages/store/StoreWrite.vue";
import StoreInfo from "@/pages/store/StoreInfo.vue";
import MyPage from "@/pages/mypage/MyPage.vue";
import ReviewEdit from "@/pages/review/ReviewEdit.vue";
import ReviewDetail from "@/pages/review/ReviewDetail.vue";
import ReviewTest from "@/pages/review/ReviewTest.vue";
import ReviewWrite from "@/pages/review/ReviewWrite.vue";
import PostWrite from "@/pages/post/PostWrite.vue";
import PostDetail from "@/pages/post/PostDetail.vue";

const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: "/", component: Home},
    { path: "/search", component: Search },
    { path: "/post", component: Post },
    { path: "/post/edit/:postId", component: PostWrite},
    { path: "/post/write", component: PostWrite},
    { path: "/post/:postId", component: PostDetail},
    { path: "/store", component: Store },
    { path: "/store/:storeId", component: StoreInfo },
    { path: "/store/write", component: StoreWrite },
    { path: "/store/edit/:storeId", component: StoreWrite },
    { path: "/mypage", component: MyPage },
    { path: "/review/:reviewId", component: ReviewDetail},
    { path: "/review/edit/:reviewId", component: ReviewEdit},
    { path: "/review/write/store/:storeId", component: ReviewWrite},
    { path: "/test", component: ReviewTest}
  ],
});
export default router;