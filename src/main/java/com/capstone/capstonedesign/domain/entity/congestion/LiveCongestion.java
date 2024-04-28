package com.capstone.capstonedesign.domain.entity.congestion;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class LiveCongestion {
    @Id
    private long id = 1;
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
