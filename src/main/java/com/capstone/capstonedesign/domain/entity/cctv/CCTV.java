package com.capstone.capstonedesign.domain.entity.cctv;

import com.capstone.capstonedesign.domain.entity.congestion.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
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
    private List<Congestion> congestions = new ArrayList<>();

    @OneToOne(mappedBy = "cctv")
    private LiveCongestion liveCongestion = null;

    @OneToMany(mappedBy = "cctv")
    @Setter
    private List<HourlyCongestion> hourlyCongestions = new ArrayList<>();

    @OneToMany(mappedBy = "cctv")
    @Setter
    private List<DayOfWeekCongestion> dayOfWeekCongestions = new ArrayList<>();

    @OneToMany(mappedBy = "cctv")
    @Setter
    private List<MonthlyCongestion> monthlyCongestions = new ArrayList<>();

    @Builder
    public CCTV(String section, int floor) {
        this.section = section;
        this.floor = floor;
    }

    public void updateLiveCongestion(LiveCongestion liveCongestion, Congestion congestion) {
        if (this.liveCongestion == null) {
            this.liveCongestion = liveCongestion;
        } else {
            this.liveCongestion.updateStatus(congestion);
        }
    }
}
