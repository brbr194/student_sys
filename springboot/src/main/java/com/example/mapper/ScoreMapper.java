package com.example.mapper;

import com.example.entity.Score;
import com.example.entity.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ScoreMapper {
    @Insert("insert into score (course_id, student_id, score, comment, feedback, semester) " +
            "VALUES (#{courseId},#{studentId},#{score},#{comment},#{feedback}, #{semester})")
    void insert(Score score);


    List<Score> selectAll(Score score);

    @Update("update score set score = #{score}, comment = #{comment}, feedback = #{feedback}, state = #{state} where id = #{id}")
    void update(Score score);

    @Select("select * from score where student_id = #{studentId} and course_id = #{courseId}")
    Score selectByCondition(Score score);

    @Delete("delete from score where id = #{id}")
    void deleteById(Integer id);

    List<Score> selectSubmittalScore(Score score);

    @Select("select max(score) from score where course_id = #{id} and state = '审核通过'")
    Double getMax(Integer id);

    @Select("select min(score) from score where course_id = #{id} and state = '审核通过'")
    Double getMin(Integer id);

    @Select("select avg(score) from score where course_id = #{id} and state = '审核通过'")
    double getAvg(Integer id);

    @Select("select count(*) from score where course_id = #{id} and score >= 60 and state = '审核通过'")
    int getUpCnt(Integer id);
    @Select("select count(*) from score where course_id = #{id} and score < 60 and state = '审核通过'")
    int getLowCnt(Integer id);
    @Select("select count(*) from score where course_id = #{id} and state = '审核通过' ")
    int getAllCnt(Integer id);

    @Select("select count(score) from score where score>= #{low}  and score <= #{up} and course_id = #{id} and state = '审核通过'")
    int getDifferentBandsCnt(double low, double up,int id);


    @Select("select * from score where course_id = #{id} and state = '审核通过'")
    List<Score> findScoreByCourseId(Integer id);

    @Select("select * from score")
    List<Score> findAll();

    List<Score> adminExport();

    List<Score> teacherExport(Score score);


    Score findByCondition(Score score);

    @Select("select * from score where id = #{id}")
    Score findById(Integer id);
}
