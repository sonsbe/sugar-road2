package com.example.sugarroad2.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

@Setter
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PostComment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "post_comment_id", updatable = false, insertable=false)
	private int id;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "user_id")
	private Users users;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "post_id")
	private Post post;

	private String content;

	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	@Column(name = "posted_date")
	private LocalDateTime postedDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "parent_comment", referencedColumnName = "post_comment_id")
	private PostComment parent;
}
