package io.untaek.animal.firebase

import io.untaek.animal.R
import java.util.*
import kotlin.collections.ArrayList

object dummy {

    val post = Post("sdf" ,User("sdf", "sdfsdf", "hahaha.jpg"), "svdvdvdvdf", Content(Type.Image, 600, 800, "hahaha.jpg"), mapOf(Pair("0", "강아지"), Pair("1", "귀여움")), 0L, 0, arrayListOf(), Date())

    val postList = ArrayList<Post>().apply {
        add(Post("sdf" ,User("sdf", "sdfsdf", "hahaha.jpg"), "svdvdvdvdf", Content(Type.Image, 300, 165, "dg1.jpg"), mapOf(Pair("0", "강아지"), Pair("1", "귀여움")), 0L, 0, arrayListOf(), Date()))
        add(Post("sdf" ,User("sdf", "sdfsdf", "hahaha.jpg"), "svdvdvdvdf", Content(Type.Video, 1000, 600, "hoho.mp4"), mapOf(Pair("0", "강아지"), Pair("1", "귀여움")), 0L, 0, arrayListOf(), Date()))
        add(Post("sdf" ,User("sdf", "sdfsdf", "hahaha.jpg"), "svdvdvdvdf", Content(Type.Image, 1080, 1920, "dg2.jpg"), mapOf(Pair("0", "강아지"), Pair("1", "귀여움")), 0L, 0, arrayListOf(), Date()))
        add(Post("sdf" ,User("sdf", "sdfsdf", "hahaha.jpg"), "svdvdvdvdf", Content(Type.Video, 720, 1280, "gg.mp4"), mapOf(Pair("0", "강아지"), Pair("1", "귀여움")), 0L, 0, arrayListOf(), Date()))
        add(Post("sdf" ,User("sdf", "sdfsdf", "hahaha.jpg"), "svdvdvdvdf", Content(Type.Video, 720, 1280, "ㅎㅇ.mp4"), mapOf(Pair("0", "강아지"), Pair("1", "귀여움")), 0L, 0, arrayListOf(), Date()))
        add(Post("sdf" ,User("sdf", "sdfsdf", "hahaha.jpg"), "svdvdvdvdf", Content(Type.Image, 600, 800, "hahaha.jpg"), mapOf(Pair("0", "강아지"), Pair("1", "귀여움")), 0L, 0, arrayListOf(), Date()))
        add(Post("sdf" ,User("sdf", "sdfsdf", "hahaha.jpg"), "svdvdvdvdf", Content(Type.Image, 600, 800, "hahaha.jpg"), mapOf(Pair("0", "강아지"), Pair("1", "귀여움")), 0L, 0, arrayListOf(), Date()))
        add(Post("sdf" ,User("sdf", "sdfsdf", "hahaha.jpg"), "svdvdvdvdf", Content(Type.Image, 300, 165, "dg1.jpg"), mapOf(Pair("0", "강아지"), Pair("1", "귀여움")), 0L, 0, arrayListOf(), Date()))
        add(Post("sdf" ,User("sdf", "sdfsdf", "hahaha.jpg"), "svdvdvdvdf", Content(Type.Video, 600, 800, "hoho.mp4"), mapOf(Pair("0", "강아지"), Pair("1", "귀여움")), 0L, 0, arrayListOf(), Date()))

    }

    val postComment: List<Comment> =
            arrayListOf(
                    Comment("com1",R.mipmap.ic_launcher,"AAA","inje","오늘 10시 30분", "개웃기네 ㅋㅋ"),
                    Comment("com2",R.mipmap.ic_launcher,"BBB","untaek","오늘 10시 40분", "저는 장문 충으로서" +
                            "할말은 없지만 칸은 채워야해서 아무말 대잔치를 강남 건물주 아프리카티브이 네이버 웹툰 아아아 할말없다 암니란ㄹ일날 ㅇ심심ㅅ해 빨리 끝내자안ㄹㄴㅇ 졸업프로젝트" +
                            "ㄹ나ㅣㄹㄴ미ㅏ"),
                    Comment("com3",R.mipmap.ic_launcher,"CCC","jonghyun","오늘 10시 40분", "개웃기네 ㅋㅋ")
            )

    private val pet111: List<PostInTimeline> =
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
    private val pet222: List<PostInTimeline> =
            arrayListOf(
                    PostInTimeline("BY7J1GGG", R.mipmap.ic_launcher, "AAA", "2", "inje", "222", "똥 잘 싼다", 4857),
                    PostInTimeline("VR1F2342", R.mipmap.ic_launcher, "AAA", "2", "inje", "222", "못생겼당 ㅋ", 54),
                    PostInTimeline("SD1235SD", R.mipmap.ic_launcher, "AAA", "2", "inje", "222", "정말루 귀엽당 ㅎ", 123)

            )
    private val pet333: List<PostInTimeline> =
            arrayListOf(
                    PostInTimeline("SDF235SD", R.mipmap.ic_launcher, "BBB", "3", "untaek", "333", "정말루 귀엽당 ㅎ", 123),
                    PostInTimeline("VR1F2342", R.mipmap.ic_launcher, "BBB", "3", "untaek", "333", "못생겼당 ㅋ", 54),
                    PostInTimeline("SD1235SD", R.mipmap.ic_launcher, "BBB", "3", "untaek", "333", "정말루 귀엽당 ㅎ", 123)
            )
    private val pet11: List<PostInTimeline> =
            arrayListOf(
                    PostInTimeline("SDF235SD", R.mipmap.ic_launcher, "CCC", "2", "jonghyun", "11", "정말루 귀엽당 ㅎ", 123),
                    PostInTimeline("VR3F1342", R.mipmap.ic_launcher, "CCC", "2", "jonghyun", "11", "못생겼당 ㅋ", 54))
    private val pet22: List<PostInTimeline> =
            arrayListOf(
                    PostInTimeline("SDF232SD", R.mipmap.ic_launcher, "CCC", "3", "jonghyun", "22", "정말루 귀엽당 ㅎ", 123),
                    PostInTimeline("SDF135SD", R.mipmap.ic_launcher, "CCC", "3", "jonghyun", "22", "정말루 귀엽당 ㅎ", 123)
            )
    private val pet33: List<PostInTimeline> =
            arrayListOf(
                    PostInTimeline("SDF232SD", R.mipmap.ic_launcher, "CCC", "3", "jonghyun", "33", "정말루 귀엽당 ㅎ", 123),
                    PostInTimeline("SDF135SD", R.mipmap.ic_launcher, "CCC", "3", "jonghyun", "33", "정말루 귀엽당 ㅎ", 123)
            )
    private val pet44: List<PostInTimeline> =
            arrayListOf(
                    PostInTimeline("SDF232SD", R.mipmap.ic_launcher, "CCC", "3", "jonghyun", "44", "정말루 귀엽당 ㅎ", 123),
                    PostInTimeline("SDF135SD", R.mipmap.ic_launcher, "CCC", "3", "jonghyun", "44", "정말루 귀엽당 ㅎ", 123)
            )
    private val pet55: List<PostInTimeline> =
            arrayListOf(
                    PostInTimeline("SDF232SD", R.mipmap.ic_launcher, "CCC", "3", "jonghyun", "55", "정말루 귀엽당 ㅎ", 123),
                    PostInTimeline("SDF135SD", R.mipmap.ic_launcher, "CCC", "3", "jonghyun", "55", "정말루 귀엽당 ㅎ", 123)
            )
    private val pet66: List<PostInTimeline> =
            arrayListOf(
                    PostInTimeline("SDF232SD", R.mipmap.ic_launcher, "CCC", "3", "jonghyun", "66", "정말루 귀엽당 ㅎ", 123),
                    PostInTimeline("SDF135SD", R.mipmap.ic_launcher, "CCC", "3", "jonghyun", "66", "정말루 귀엽당 ㅎ", 123)
            )
    private val pet777: List<PostInTimeline> =
            arrayListOf(PostInTimeline("SDF235SD", R.mipmap.ic_launcher, "DDD", "7", "taeyang", "777", "정말루 귀엽당 ㅎ", 123))
    private val pet888: List<PostInTimeline> =
            arrayListOf(
                    PostInTimeline("SDF235SD", R.mipmap.ic_launcher, "EEE", "8", "sungbum", "888", "정말루 귀엽당 ㅎ", 123),
                    PostInTimeline("VR3F1342", R.mipmap.ic_launcher, "EEE", "8", "sungbum", "888", "못생겼당 ㅋ", 54)
            )

    private val injePost: List<List<PostInTimeline>> =
            arrayListOf(pet111, pet222)
    private val untaekPost: List<List<PostInTimeline>> =
            arrayListOf(pet333)
    private val jonghyunPost: List<List<PostInTimeline>> =
            arrayListOf(pet11, pet22, pet33, pet44, pet55, pet66)
    private val taeyangPost: List<List<PostInTimeline>> =
            arrayListOf(pet777)
    private val sungbumPost: List<List<PostInTimeline>> =
            arrayListOf(pet888)

    val usersPost: List<List<List<PostInTimeline>>> =
            arrayListOf(
                    injePost,
                    untaekPost,
                    jonghyunPost,
                    taeyangPost,
                    sungbumPost
            )
    val usersDetail: List<UserDetail> =
            arrayListOf(
                    UserDetail("AAA","inje",R.mipmap.ic_launcher,7421,24,300),
                    UserDetail("BBB","untaek",R.mipmap.ic_launcher,3333,14,255),
                    UserDetail("CCC","jonghyun",R.mipmap.ic_launcher,2224,11,226),
                    UserDetail("DDD","taeyang",R.mipmap.ic_launcher,366,2,44),
                    UserDetail("EEE","sungbum",R.mipmap.ic_launcher,442,5,31)
            )
    val timelines: ArrayList<PostInTimeline> =
            arrayListOf(
                    PostInTimeline("SDF235SD", R.mipmap.ic_launcher, "SDFH23D2", "NUMUYFH535", "Anthony", "강즤쥐", "정말루 귀엽당 ㅎ", 123),
                    PostInTimeline("VR3F2342", R.mipmap.ic_launcher, "SDFH23D2", "NUMUYFH535", "Anthony", "강즤쥐", "못생겼당 ㅋ", 54),
                    PostInTimeline("BY7J5GGG", R.mipmap.ic_launcher, "WTERGWGD", "DGDED43SDV", "Adelina", "Poopy", "똥 잘 싼다", 4857)
            )
    val grid_posts: List<PostInTimeline> =
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
