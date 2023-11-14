package com.example.sugarroad2.controller;

import com.example.sugarroad2.model.dto.request.UsersRequestDTO;
import com.example.sugarroad2.model.dto.response.UsersResponseDTO;
import com.example.sugarroad2.model.entity.Users;
import com.example.sugarroad2.service.UsersService;
import com.example.sugarroad2.util.ImageUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MypageController {

    @Autowired
    UsersService usersService;

//    @Autowired
//    PostDAO postDAO;
//    @Autowired
//    PostImageDAO postImageDAO;

    @Autowired
    ImageUtil imageUtil;

    @RequestMapping({"/mypage", "/mypage/edit", "/mypage/delete"})
    public ModelAndView userInfo(HttpSession session,
                                 HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        String requestUrl = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        //리퀘스트 받은 URL 정보
        if (requestUrl.equals("/mypage")) { //mypage에서 리퀘스트 받으면
            mav.setViewName("/mypage/index"); //mypage/index로 전달
        }

        if (requestUrl.equals("/mypage/edit")) { //mypage/edit에서 리퀘스트 받으면
            mav.setViewName("/mypage/edit"); //mypage/edit로 전달
        }

        if (requestUrl.equals("/mypage/delete")) { //mypage/delete에서 리퀘스트 받으면
            mav.setViewName("/mypage/delete"); //mypage/delete로 전달
        }

        //현재 유저 정보를 DB조회하는 코드
        //String nowLoginId = (String) session.getAttribute("nowLogin");
        String nowLoginId = "테스터ID";
        System.out.println("현재 로그인 중인 ID : " + nowLoginId); //현재 로그인 중인 ID

        Users nowUserSelect = usersService.selectId(nowLoginId);

        if (nowUserSelect.getUserId().isEmpty()) {
            mav.addObject("msg", "유저 정보를 불러오지 못했습니다");
        } else {
            mav.addObject("userInfo", new UsersResponseDTO(nowUserSelect));
        }
        return mav;
    }

    @RequestMapping(value = "/users/edit", method = RequestMethod.POST)
    public String usersEdit(UsersRequestDTO editUser,
                            Model model) { //id, pw, 이름, 별명, 이메일 정보 받고

        MultipartFile imageFile = editUser.getImage();

        if (!(imageFile.isEmpty())) { //입력된 파일이 없지 않으면 이미지 삽입 실행
            String userImagePath = imageUtil.writeImage(imageFile);
            editUser.setUserImagePath(userImagePath);
        }

        if (usersService.save(editUser, editUser.getUserImagePath())) {
            System.out.println("회원의 정보가 수정되었습니다");
            model.addAttribute("msg", editUser.getId() + "회원의 정보가 수정되었습니다");
            return "redirect:/mypage";
        } else {
            System.out.println("회원정보 수정에 실패했습니다");
            model.addAttribute("msg", "회원정보 수정에 실패했습니다");
            return "/mypage";
        }
    }

    @RequestMapping(value = "/logout")
    public String logOut(HttpServletRequest request) {
        //세션 객체가 있으면 새로 생성하지 않고 세션을 가져옴
        HttpSession session = request.getSession(false);

        if (session != null) { //세션이 존재하면
            session.invalidate(); //세션 초기화 (종료 아님)
        }
        System.out.println("로그아웃");
        return "redirect:/users/login.html"; //로그인 화면으로 돌아감
    }

    @RequestMapping(value = "/users/delete")
    public String usersDelete(UsersRequestDTO deleteUser,
                              HttpServletRequest request,
                              Model model) {

        //세션 객체가 있으면 새로 생성하지 않고 세션을 가져옴
        HttpSession session = request.getSession(false);

        //현재 유저 정보를 DB조회하는 코드
        //String nowLoginId = (String) session.getAttribute("nowLogin");
        String nowLoginId = "테스터ID";

        Users selectDeleteId = usersService.selectId(nowLoginId);
        //삭제하려는 회원정보 조회

        if(!(selectDeleteId.getUserPassword().equals(deleteUser.getUserPassword()))){
            //입력한 패스워드와 일치하지 않으면
            System.out.println("패스워드를 다시 입력해주세요");
            model.addAttribute("msg", "패스워드를 다시 입력해주세요");
            return "redirect:/mypage/delete";
        } else {
            if(usersService.delete(deleteUser)){ //삭제하려는 유저의 정보를 삭제

                if(session != null){ //세션이 존재하면
                    session.invalidate(); //세션 초기화
                }

                System.out.println("탈퇴되었습니다");
                return "redirect:/users/login.html"; //로그인 화면으로 이동
            }
        }
        System.out.println("오류가 발생했습니다");
        return "redirect:/users/login.html";
    }

//    @GetMapping("/mypage/mypost")
//    public ModelAndView readMyPost(HttpSession session){
//        ModelAndView mav = new ModelAndView();
//        String userId = (String) session.getAttribute("nowLogin");
//        List<PostDTO> postList = postDAO.readPostBy("user_id", userId);
//        if(!postList.isEmpty()) {
//            for (PostDTO p : postList) {
//                int id = p.getPostId();
//                List<String> iList = postImageDAO.readPostImage(id);
//                if (iList != null) {
//                    p.setPostImage(iList.toArray(new String[0]));
//                }
//            }
//
//            mav.addObject("postList", postList);
//        }
//        else
//            mav.addObject("msg", "작성한 글이 없습니다.");
//
//        mav.setViewName("mypage/mypost");
//
//        return mav;
//    }

//    @GetMapping("/mypage/mylike")
//    public ModelAndView readMyRecommendation(HttpSession session){
//        ModelAndView mav = new ModelAndView();
//        String userId = (String) session.getAttribute("nowLogin");
//        List<PostDTO> postList = postDAO.readRecommedingPostbyUser(userId);
//        if(!postList.isEmpty()) {
//            for (PostDTO p : postList) {
//                int id = p.getPostId();
//                List<String> iList = postImageDAO.readPostImage(id);
//                if (iList != null) {
//                    p.setPostImage(iList.toArray(new String[0]));
//                }
//            }
//            mav.addObject("postList", postList);
//        }
//        else{
//            mav.addObject("msg", "작성한 글이 없습니다.");
//        }
//
//        mav.setViewName("mypage/mylike");
//
//        return mav;
//    }

}

