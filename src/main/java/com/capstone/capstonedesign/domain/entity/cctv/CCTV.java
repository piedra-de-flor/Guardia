package com.capstone.capstonedesign.domain.entity.cctv;

import com.capstone.capstonedesign.domain.entity.congestion.*;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class CCTV {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int floor;
    private String section;

    @OneToMany(mappedBy = "cctv")
    private List<Congestion> congestions;

    @OneToOne(mappedBy = "cctv")
    private LiveCongestion liveCongestion;

    @OneToMany(mappedBy = "cctv")
    private List<HourlyCongestion> hourlyCongestionList;

    @OneToMany(mappedBy = "cctv")
    private List<DayOfWeekCongestion> dayOfWeekCongestions;

    @OneToMany(mappedBy = "cctv")
    private List<MonthlyCongestion> monthlyCongestions;
}
