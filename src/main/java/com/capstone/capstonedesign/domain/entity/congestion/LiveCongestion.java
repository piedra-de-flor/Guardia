package com.capstone.capstonedesign.domain.entity.congestion;

import com.capstone.capstonedesign.domain.entity.cctv.CCTV;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class LiveCongestion {
    @Id
    @OneToOne
    private CCTV cctv;

    @OneToOne
    @JoinColumn(name = "dateTime")
    private Congestion congestion;

    public void updateStatus(Congestion congestion) {
        this.congestion = congestion;
    }

    public LiveCongestion(Congestion congestion) {
        this.congestion = congestion;
    }
}
