package io.untaek.animal.firebase

import io.untaek.animal.R
import java.util.*
import kotlin.collections.ArrayList

object dummy {

    val jpeg = "image/jpeg"
    val mp4 = "video/mp4"

    val post = Post("sdf" ,User("sdf", "sdfsdf", "hahaha.jpg"), "svdvdvdvdf", Content(jpeg, 600, 800, "hahaha.jpg"), mapOf(Pair("0", "강아지"), Pair("1", "귀여움")), 0L, 0, arrayListOf(), Date())


    val commentList = ArrayList<Comment2>().apply {
        add(Comment2("AAAAA",User("A1B2C3","inje","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQitUl14tC2Ld3WVEUU0fXNoRx_oGQjgCf8QLXi-gKlbr0EJFKRFDO9OfYj"),Date(),"ㅋㅋㅋㅋ 1"))
        add(Comment2("BBBBB",User("A1B2C3","inje","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQitUl14tC2Ld3WVEUU0fXNoRx_oGQjgCf8QLXi-gKlbr0EJFKRFDO9OfYj"),Date(),"ㅋㅋㅋㅋ 2"))
        add(Comment2("CCCCC",User("A1B2C3","inje","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQitUl14tC2Ld3WVEUU0fXNoRx_oGQjgCf8QLXi-gKlbr0EJFKRFDO9OfYj"),Date(),"ㅋㅋㅋㅋ 3"))
        add(Comment2("AAAAA",User("A1B2C3","inje","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQitUl14tC2Ld3WVEUU0fXNoRx_oGQjgCf8QLXi-gKlbr0EJFKRFDO9OfYj"),Date(),"ㅋㅋㅋㅋ 4"))
        add(Comment2("BBBBB",User("A1B2C3","inje","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQitUl14tC2Ld3WVEUU0fXNoRx_oGQjgCf8QLXi-gKlbr0EJFKRFDO9OfYj"),Date(),"ㅋㅋㅋㅋ 5"))
        add(Comment2("CCCCC",User("A1B2C3","inje","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQitUl14tC2Ld3WVEUU0fXNoRx_oGQjgCf8QLXi-gKlbr0EJFKRFDO9OfYj"),Date(),"ㅋㅋㅋㅋ 6"))

    }
    val img = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQitUl14tC2Ld3WVEUU0fXNoRx_oGQjgCf8QLXi-gKlbr0EJFKRFDO9OfYj"
    val postList = ArrayList<Post>().apply {
        add(Post("aa1" ,User("sdf", "sdfsdf", img), "svdvdvdvdf", Content(jpeg, 600, 800, img), mapOf(Pair("0", "강아지"), Pair("1", "귀여움")), 30L, 3, commentList, Date()))
        add(Post("aa2" ,User("sdf", "sdfsdf", img), "svdvdvdvdf", Content(jpeg, 600, 800, img), mapOf(Pair("0", "강아지"), Pair("1", "귀여움")), 1111L, 3, commentList, Date()))
        add(Post("aa3" ,User("sdf", "sdfsdf", img), "svdvdvdvdf", Content(jpeg, 600, 800, img), mapOf(Pair("0", "강아지"), Pair("1", "귀여움")), 22230L, 3, commentList, Date()))
        add(Post("aa4" ,User("sdf", "sdfsdf", img), "svdvdvdvdf", Content(jpeg, 600, 800, img), mapOf(Pair("0", "강아지"), Pair("1", "귀여움")), 40L, 3, commentList, Date()))
        add(Post("aa5" ,User("sdf", "sdfsdf", img), "svdvdvdvdf", Content(jpeg, 600, 800, img), mapOf(Pair("0", "강아지"), Pair("1", "귀여움")), 1L, 3, commentList, Date()))
        add(Post("aa6" ,User("sdf", "sdfsdf", img), "svdvdvdvdf", Content(jpeg, 600, 800, img), mapOf(Pair("0", "강아지"), Pair("1", "귀여움")), 20L, 3, commentList, Date()))
        add(Post("aa7" ,User("sdf", "sdfsdf", img), "svdvdvdvdf", Content(jpeg, 600, 800, img), mapOf(Pair("0", "강아지"), Pair("1", "귀여움")), 40L, 3, commentList, Date()))
        add(Post("aa8" ,User("sdf", "sdfsdf", img), "svdvdvdvdf", Content(jpeg, 600, 800, img), mapOf(Pair("0", "강아지"), Pair("1", "귀여움")), 50L, 3, commentList, Date()))
        add(Post("aa9" ,User("sdf", "sdfsdf", img), "svdvdvdvdf", Content(jpeg, 600, 800, img), mapOf(Pair("0", "강아지"), Pair("1", "귀여움")), 220L, 3, commentList, Date()))
    }

    val postComment: ArrayList<Comment> =
            arrayListOf(
                    Comment("com1",R.mipmap.ic_launcher,"AAA","inje","오늘 10시 30분", "개웃기네 ㅋㅋ"),
                    Comment("com2",R.mipmap.ic_launcher,"BBB","untaek","오늘 10시 40분", "저는 장문 충으로서" +
                            "할말은 없지만 칸은 채워야해서 아무말 대잔치를 강남 건물주 아프리카티브이 네이버 웹툰 아아아 할말없다 암니란ㄹ일날 ㅇ심심ㅅ해 빨리 끝내자안ㄹㄴㅇ 졸업프로젝트" +
                            "ㄹ나ㅣㄹㄴ미ㅏ"),
                    Comment("com3",R.mipmap.ic_launcher,"CCC","jonghyun","오늘 10시 40분", "개웃기네 ㅋㅋ")
            )

    private val pet111: ArrayList<PostInTimeline> =
            arrayListOf(
                    PostInTimeline("SDF235SD", R.mipmap.ic_launcher, "AAA", "1", "inje", "111", "저는 장문 충으로서" +
                            "할말은 없지만 칸은 채워야해서 아무말 대잔치를 강남 건물주 아프리카티브이 네이버 웹툰 아아아 할말없다 암니란ㄹ일날 ㅇ심심ㅅ해 빨리 끝내자안ㄹㄴㅇ 졸업프로젝트" +
                            "ㄹ나ㅣㄹㄴ미ㅏ 귀엽당 ㅎ", 123),
                    PostInTimeline("VR3F1342", R.mipmap.ic_launcher, "AAA", "1", "inje", "111", "못생겼당 ㅋ", 54),
                    PostInTimeline("SDF232SD", R.mipmap.ic_launcher, "AAA", "1", "inje", "111", "정말루 귀엽당 ㅎ", 123),
                    PostInTimeline("SDF135SD", R.mipmap.ic_launcher, "AAA", "1", "inje", "111", "정말루 귀엽당 ㅎ", 123),
                    PostInTimeline("BY4J5GGG", R.mipmap.ic_launcher, "AAA", "1", "inje", "111", "똥 잘 싼다", 4857),
                    PostInTimeline("VR2F2342", R.mipmap.ic_launcher, "AAA", "1", "inje", "111", "못생겼당 ㅋ", 54)
            )
    private val pet222: ArrayList<PostInTimeline> =
            arrayListOf(
                    PostInTimeline("BY7J1GGG", R.mipmap.ic_launcher, "AAA", "2", "inje", "222", "똥 잘 싼다", 4857),
                    PostInTimeline("VR1F2342", R.mipmap.ic_launcher, "AAA", "2", "inje", "222", "못생겼당 ㅋ", 54),
                    PostInTimeline("SD1235SD", R.mipmap.ic_launcher, "AAA", "2", "inje", "222", "정말루 귀엽당 ㅎ", 123)

            )
    private val pet333: ArrayList<PostInTimeline> =
            arrayListOf(
                    PostInTimeline("SDF235SD", R.mipmap.ic_launcher, "BBB", "3", "untaek", "333", "정말루 귀엽당 ㅎ", 123),
                    PostInTimeline("VR1F2342", R.mipmap.ic_launcher, "BBB", "3", "untaek", "333", "못생겼당 ㅋ", 54),
                    PostInTimeline("SD1235SD", R.mipmap.ic_launcher, "BBB", "3", "untaek", "333", "정말루 귀엽당 ㅎ", 123)
            )
    private val pet11: ArrayList<PostInTimeline> =
            arrayListOf(
                    PostInTimeline("SDF235SD", R.mipmap.ic_launcher, "CCC", "2", "jonghyun", "11", "정말루 귀엽당 ㅎ", 123),
                    PostInTimeline("VR3F1342", R.mipmap.ic_launcher, "CCC", "2", "jonghyun", "11", "못생겼당 ㅋ", 54))
    private val pet22: ArrayList<PostInTimeline> =
            arrayListOf(
                    PostInTimeline("SDF232SD", R.mipmap.ic_launcher, "CCC", "3", "jonghyun", "22", "정말루 귀엽당 ㅎ", 123),
                    PostInTimeline("SDF135SD", R.mipmap.ic_launcher, "CCC", "3", "jonghyun", "22", "정말루 귀엽당 ㅎ", 123)
            )
    private val pet33: ArrayList<PostInTimeline> =
            arrayListOf(
                    PostInTimeline("SDF232SD", R.mipmap.ic_launcher, "CCC", "3", "jonghyun", "33", "정말루 귀엽당 ㅎ", 123),
                    PostInTimeline("SDF135SD", R.mipmap.ic_launcher, "CCC", "3", "jonghyun", "33", "정말루 귀엽당 ㅎ", 123)
            )
    private val pet44: ArrayList<PostInTimeline> =
            arrayListOf(
                    PostInTimeline("SDF232SD", R.mipmap.ic_launcher, "CCC", "3", "jonghyun", "44", "정말루 귀엽당 ㅎ", 123),
                    PostInTimeline("SDF135SD", R.mipmap.ic_launcher, "CCC", "3", "jonghyun", "44", "정말루 귀엽당 ㅎ", 123)
            )
    private val pet55: ArrayList<PostInTimeline> =
            arrayListOf(
                    PostInTimeline("SDF232SD", R.mipmap.ic_launcher, "CCC", "3", "jonghyun", "55", "정말루 귀엽당 ㅎ", 123),
                    PostInTimeline("SDF135SD", R.mipmap.ic_launcher, "CCC", "3", "jonghyun", "55", "정말루 귀엽당 ㅎ", 123)
            )
    private val pet66: ArrayList<PostInTimeline> =
            arrayListOf(
                    PostInTimeline("SDF232SD", R.mipmap.ic_launcher, "CCC", "3", "jonghyun", "66", "정말루 귀엽당 ㅎ", 123),
                    PostInTimeline("SDF135SD", R.mipmap.ic_launcher, "CCC", "3", "jonghyun", "66", "정말루 귀엽당 ㅎ", 123)
            )
    private val pet777: ArrayList<PostInTimeline> =
            arrayListOf(PostInTimeline("SDF235SD", R.mipmap.ic_launcher, "DDD", "7", "taeyang", "777", "정말루 귀엽당 ㅎ", 123))
    private val pet888: ArrayList<PostInTimeline> =
            arrayListOf(
                    PostInTimeline("SDF235SD", R.mipmap.ic_launcher, "EEE", "8", "sungbum", "888", "정말루 귀엽당 ㅎ", 123),
                    PostInTimeline("VR3F1342", R.mipmap.ic_launcher, "EEE", "8", "sungbum", "888", "못생겼당 ㅋ", 54)
            )

    private val injePost: ArrayList<ArrayList<PostInTimeline>> =
            arrayListOf(pet111, pet222)
    private val untaekPost: ArrayList<ArrayList<PostInTimeline>> =
            arrayListOf(pet333)
    private val jonghyunPost: ArrayList<ArrayList<PostInTimeline>> =
            arrayListOf(pet11, pet22, pet33, pet44, pet55, pet66)
    private val taeyangPost: ArrayList<ArrayList<PostInTimeline>> =
            arrayListOf(pet777)
    private val sungbumPost: ArrayList<ArrayList<PostInTimeline>> =
            arrayListOf(pet888)

    val usersPost: ArrayList<ArrayList<ArrayList<PostInTimeline>>> =
            arrayListOf(
                    injePost,
                    untaekPost,
                    jonghyunPost,
                    taeyangPost,
                    sungbumPost
            )

    val usersDetail: ArrayList<UserDetail> =
            arrayListOf(
                    UserDetail("AAA","inje",img,7421,24,300),
                    UserDetail("BBB","untaek",img,3333,14,255),
                    UserDetail("CCC","jonghyun",img,2224,11,226),
                    UserDetail("DDD","taeyang",img,366,2,44),
                    UserDetail("EEE","sungbum",img,442,5,31)
            )
    val timelines: ArrayList<PostInTimeline> =
            arrayListOf(
                    PostInTimeline("SDF235SD", R.mipmap.ic_launcher, "SDFH23D2", "NUMUYFH535", "Anthony", "강즤쥐", "정말루 귀엽당 ㅎ", 123),
                    PostInTimeline("VR3F2342", R.mipmap.ic_launcher, "SDFH23D2", "NUMUYFH535", "Anthony", "강즤쥐", "못생겼당 ㅋ", 54),
                    PostInTimeline("BY7J5GGG", R.mipmap.ic_launcher, "WTERGWGD", "DGDED43SDV", "Adelina", "Poopy", "똥 잘 싼다", 4857)
            )
    val grid_posts: ArrayList<PostInTimeline> =
            arrayListOf(
                    PostInTimeline("SDF235SD", R.mipmap.ic_launcher, "AAA", "1", "inje", "111", "정말루 귀엽당 ㅎ", 123),
                    PostInTimeline("VR3F1342", R.mipmap.ic_launcher, "AAA", "1", "inje", "111", "못생겼당 ㅋ", 54),
                    PostInTimeline("SDF232SD", R.mipmap.ic_launcher, "AAA", "1", "inje", "111", "정말루 귀엽당 ㅎ", 123),
                    PostInTimeline("SDF135SD", R.mipmap.ic_launcher, "AAA", "1", "inje", "111", "정말루 귀엽당 ㅎ", 123),
                    PostInTimeline("BY4J5GGG", R.mipmap.ic_launcher, "AAA", "1", "inje", "111", "똥 잘 싼다", 4857),
                    PostInTimeline("VR2F2342", R.mipmap.ic_launcher, "AAA", "1", "inje", "111", "못생겼당 ㅋ", 54),
                    PostInTimeline("VR1F2342", R.mipmap.ic_launcher, "AAA", "2", "inje", "222", "못생겼당 ㅋ", 54),
                    PostInTimeline("SDF235SD", R.mipmap.ic_launcher, "BBB", "3", "untaek", "333", "정말루 귀엽당 ㅎ", 123),
                    PostInTimeline("VR1F2342", R.mipmap.ic_launcher, "BBB", "3", "untaek", "333", "못생겼당 ㅋ", 54)
            )

}
