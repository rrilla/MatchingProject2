import React, { useState, useEffect } from 'react';
import { Container, Jumbotron, Button, Form, FormControl, Row, Col, Modal } from 'react-bootstrap';
import Party from '../../components/Party';
import Background from '../../components/Background';
import { Link } from 'react-router-dom';
import styled from 'styled-components';
import MatchingCard from '../../components/MatchingCard';

const MainCardStyle = styled.div`
  width: 100%;
  margin: auto;
`;

const LinkStyle = styled.span`
  color : black;
`;

const SlideStyle = styled.div`
	margin-top:15%;
	margin-bottom:4%;
`;

const MyTeam = () => {

	const [searchUser, setSearchUser] = useState({
		nickname: "",
		location: "",
		position: "",
		id: null
	});
	const [winner, setWinner] = useState(null);

	const [isSearch, setIsSearch] = useState(false);
	const [schedule, setSchedule] = useState([]);

	const inputHandle = (e) => {
		setSearchUser({
			...searchUser,
			[e.target.name]: e.target.value,
		});
	};

	const as = () => {
		console.log(winner);///user/scoreWiner/{battleid}
		fetch(`http://localhost:8000/user/scoreWiner/${deId.id}`, {
			method: "put",
			headers: {
				//'Content-Type': "application/json; charset=utf-8",
				'Authorization': localStorage.getItem("Authorization")
			}
		}).then((res) => res.text())
			.then(res => {
				console.log(res);
				if (res === "ok") alert("완료");
				else alert("실패");
			});
	}

	const [deId, setDeId] = useState({
		id: null,
		myTeam: null,
		jteam: null
	}); // 자세히 보기 팀 id


	const [show, setShow] = useState(false);
	const handleClose = () => setShow(false);
	const handleShow = () => { setShow(true) };
	const [show2, setShow2] = useState(false);
	const handleClose2 = () => setShow2(false);

	const handleShow2 = (id, myTeam, jteam) => {
		setShow2(true);
		setDeId({
			id: id,
			myTeam: myTeam,
			jteam: jteam
		});
	};

	const cheo = (userid) => {
		fetch(`http://localhost:8000/user/apply2/${userid}`, {
			method: "post",
			headers: {
				'Content-Type': "application/json; charset=utf-8",
				'Authorization': localStorage.getItem("Authorization")
			}
		}).then((res) => res.text())
			.then(res => {
				console.log(res);
				if (res === "ok") alert("팀가입 요청 완료");
				else alert("팀가입 요청 실패");
			});
	};

	const searchUserfunction = (nick) => {
		alert(nick + "검색되었습니다");
		// fetch로 검색해서 searchUser에 넣기

		fetch(`http://localhost:8000/nicknameDetail/${nick}`, {
			method: "get",
		}).then((res) => res.json())
			.then(res => {
				console.log("닉네임으로 검색 결과", res);
				setSearchUser(res);
			});
		setIsSearch(true);
	}

	const searchResult =
		<div>
			usernickname : {searchUser.nickname} <br />
				usernicklocation : {searchUser.location} <br />
				usernickposition : {searchUser.position} <br />
		</div>


	// teaminfo create
	const sss = () => {
		let teamInfo = {
			/* loginid: user.loginid,
			password: user.password */
			//user1: {id: 1},
			user2: { id: 1 },
			user3: { id: 1 },
			user4: { id: 1 },
			user5: { id: 1 },
			user6: { id: 1 },
			user7: { id: 1 },
			user8: { id: 1 },
			user9: { id: 1 },
			user10: { id: 1 },
			user11: { id: 1 },

		}

		fetch(`http://localhost:8000/user/teamInfo`, {
			method: "post",
			body: JSON.stringify(teamInfo),
			headers: {
				'Content-Type': "application/json; charset=utf-8",
				'Authorization': localStorage.getItem("Authorization")
			}
		}).then((res) => res.text())
			.then(res => {
				if (res === "ok") alert("tema info create");
				else alert("팀가입 요청 실패");
			});
	};

	// 새로 고침없이 바로 수정되게 하려면 state 등록해야함 / 귀찮으니까 나중에
	const joinTeamReq = (partyid) => {
		alert("ddd");

		fetch(`http://localhost:8000/Acknowledgment/${partyid}`, {
			method: "put",
		}).then((res) => {
			console.log("zzz1", res);
			return res.text();
		}).then((res) => {
			console.log("zzz2", res);
		});
	};

	const zzz = (battleid) => {
		alert("ddd");
		console.log(battles.id);
		fetch(`http://localhost:8000/user/matchAccept/${battleid}`, {
			method: "put",
			headers: {
				'Authorization': localStorage.getItem("Authorization")
			}
		}).then((res) => {
			console.log("zzz1", res);
			return res.text();
		}).then((res) => {
			console.log("zzz2", res);
		});
	};

	const [team, setTeam] = useState([]);
	const { id, explaintation, name } = team;
	const [owner, setOwner] = useState([]);
	const [partys, setPartys] = useState([]);
	const [users, setUsers] = useState([]);
	const [battles, setBattles] = useState([]);

	useEffect(() => {
		fetch("http://localhost:8000/user/myTeam", {
			method: "get",
			headers: {
				'Authorization': localStorage.getItem("Authorization")
			}
		}).then((res) => {
			console.log("MyTeamForm:: login한 ID의 Team Id display res", res);
			return res.text();
		}).then((res) => {
			console.log("MyTeamForm:: login한 ID의 Team Id display", res);

			fetch(`http://localhost:8000/teamDetail/${res}`, {
				// 여기 들어가는 res는 현재 로그인 한 ID의 TeamID // 팀 정보 가져와서 소속 선수, 팀장 display
				method: "get",
			}).then((res) => {
				return res.json();
			}).then((res) => {
				console.log("MyTeam:: Team info fetch display res: ", res);
				setTeam(res);
				setOwner(res.owner);
				setUsers(res.users);
			});

			fetch(`http://localhost:8000/user/teamParty/${res}`, {
				// 여기 들어가는 res는 현재 로그인 한 ID의 TeamID // 팀 가입 요청 가져와서 display
				method: "get",
				headers: {
					'Authorization': localStorage.getItem("Authorization")
				}
			}).then((res) => {
				return res.json();
			}).then((res) => {
				console.log("MyTeam:: party list(from team) info fetch display res: ", res);
				setPartys(res);
			});

			// team battle fetch
			fetch(`http://localhost:8000/user/loginBattleList`, {
				// 여기 들어가는 res는 현재 로그인 한 ID의 TeamID // 팀 가입 요청 가져와서 display
				method: "get",
				headers: {
					'Authorization': localStorage.getItem("Authorization")
				}
			}).then((res) => {
				console.log("MyTeam:: battle1 info fetch display res: ", res);
				//const tmp = null;
				return res.json();
			}).then((res) => {
				console.log("MyTeam:: battle2 info fetch display res: ", res);
				setBattles(res);
			});

			fetch(`http://localhost:8000/battleList/${res}`, {
				method: "get",
			}).then((res) => {
				console.log("team schedule fetch fisrt then res :: ", res);
				return res.json();
			}).then((res) => {
				console.log("team schedule fetch second then res", res);
				setSchedule(res);
			});
		});
	}, []);



	return (
		<Container>

			<Modal show={show} size={"lg"} onHide={handleClose}>
				<Modal.Header closeButton>
					<Modal.Title>팀원초대</Modal.Title>
				</Modal.Header>
				<Modal.Body>
					<Form.Row>
						<Col md={2}></Col>
						<Form.Group as={Col} md={5} controlId="formGridEmail">
							<Form.Label>nickname</Form.Label>
							<Row>
								<Col md={10}>
									<Form.Control
										type="text"
										name="nickname"
										placeholder="nickname"
										onChange={inputHandle}
										value={searchUser.nickname} />
								</Col>
								<Col md={2}>
									<Button variant="secondary" onClick={() => searchUserfunction(searchUser.nickname)}>search</Button>
								</Col>
								{searchResult}
							</Row>
						</Form.Group>
					</Form.Row>
				</Modal.Body>
				<Modal.Footer>
					<Button variant="secondary" onClick={() => cheo(searchUser.id)}>초대하기</Button>
					<Button variant="secondary" onClick={handleClose}>
						Close
					</Button>
				</Modal.Footer>
			</Modal>

			<Modal show={show2} size={"lg"} onHide={handleClose}>
				<Modal.Header closeButton>
					<Modal.Title>팀원초대</Modal.Title>
				</Modal.Header>
				<Modal.Body>
					<MatchingCard></MatchingCard>
					<hr />
					승리팀 선택하기<br />
					{/* <Form.Group onSubmit={win1} controlId="exampleForm.SelectCustomSizeSm">
						<Form.Label>select winner</Form.Label>
						<Form.Control as="select" size="sm" custom>
							<option value="grapefruit">Grapefruit</option>
							<option value="lime">Lime</option>
						</Form.Control>
					</Form.Group> */}

					<Button variant="secondary" onClick={as}>{deId.myTeam}</Button>
					<Button variant="secondary" onClick={as}>{deId.jteam}</Button>
					<Button variant="secondary" onClick={as}>{deId.id}</Button>
				</Modal.Body>
				<Modal.Footer>
					<Button variant="secondary" onClick={handleClose2}>
						Close
					</Button>
				</Modal.Footer>
			</Modal>



			<SlideStyle>
				<MainCardStyle>
					<Jumbotron>
						<Row>
							<Col md={12}><h1>⚽ {name}</h1></Col>
							<Col md={12}><hr /></Col>
							<Col md={3}><h5>👑 {owner.nickname}</h5></Col>
							<Col md={8}><h5>📄 {explaintation}</h5></Col>
							<Col md={12}><hr /></Col>
							<Col md={3}><h3>🏃‍♀️ Member</h3></Col>
							<Col md={8}></Col>
							<Col md={12}><br /></Col>
							{users.map((res) => (//이 팀에 들어온 파티 번호 : {res.id}
								<Col md={3}>🏃 {res.nickname}</Col>
							))}
							<Col md={12}><hr /></Col>
							<Col md={3}><h3>🙌 가입신청</h3></Col>
							<Col md={8}><h3>{partys.length}건</h3></Col>
							{/* <Col md={6}><Button onClick={joinTeamReq}>전체수락</Button></Col> */}
							<Col md={12}><br /></Col>
							{partys.map((res) => (//이 팀에 들어온 파티 번호 : {res.id}
								<Col md={3}>
									🏃 {res.user.nickname}&nbsp;&nbsp;&nbsp;
									<Button onClick={() => joinTeamReq(res.id)}>수락</Button>
								</Col>
							))}
							<Col md={12}><hr /></Col>
							<Col md={3}><h3>⚔ 대전신청</h3></Col>
							{/* <Col md={8}><h3>{battles.length}건</h3></Col> */}
							<Col md={12}><br /></Col>

							{battles.map((res) => (
								res.role === 1
									?
									<Col md={3}>
										{/* 💥 이게 베틀 아이디{res.id}&nbsp;&nbsp;&nbsp; */}
										💥 상대편 팀 이름 {res.requestTeam.name}&nbsp;&nbsp;&nbsp;
										<Button onClick={sss} variant="outline-success">teaminfo</Button>
										<Button onClick={() => zzz(res.id)}>수락</Button>
										{/* <Button onClick={zzz}>참가명단보기</Button> */}
									</Col>
									: null
							))}
							<Col md={12}><hr /></Col>

							<Col md={3}><h3>⚔ 경기일정</h3></Col>
							{/* <Col md={9}><h3>{battles.length}건</h3></Col> */}
							<Col md={12}><br /></Col>

							<Col md={12}>
								{schedule.map((res) => (
									res.role === 2
										? <div>요청팀 = {res.requestTeam.name} vs 수락팀 = {res.responseTeam.name}
											< Button onClick={() => handleShow2(res.id, res.requestTeam.name, res.responseTeam.name)} size="sm" variant="outline-success" >자세히보기</Button>
										</div>
										: null

								))}
							</Col>

							<Col md={12}><hr /></Col>
							<Col md={3}>
								<Button onClick={handleShow} variant="outline-success">팀원초대</Button>
							</Col>


						</Row>
					</Jumbotron>
				</MainCardStyle>
			</SlideStyle >
		</Container >
	);
};
// 일단 우리팀의 id를 찾는다
// fetch로 팀 상세보기해서 우리팀 정보 가져와서 보여준다
//팀장일시 : 팀 가입 요청  목록 ) partys로 가져움 + 승인 버튼 , 추방 버튼 
// localhost:8000/givemeid 요청을 보내면 그 유저가 어떤 유저인지 파악해서 id를 리턴해주는 걸 만들면 되겠지?
export default MyTeam;