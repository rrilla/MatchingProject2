import React from 'react';
import { ListGroup, Card, ListGroupItem, Row, Col } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import styled from 'styled-components';

const LinkStyle = styled.span`
    color : black;
  `;

const UserCard = (props) => {
	console.log("UserCard:: props data: ", props);


	//const memberCount = props.team.users.length;

//포지션이랑 팀 정보 나중에 추가하기 
	const { username, nickname, location, image} = props.user;
	// const nickname = owner.nickname;
	// const url = "/Team_detail/"+id;

	return (
		<div>
		<Card style={{ width: '18rem' }}>
			{/* 이미지 나중에 고치기  */}
			<Card.Img variant="top" src="1slideepic.png" />
			<Card.Body>
				<Card.Title>🧑 닉네임 : {nickname} </Card.Title>
				<Card.Text>
					📍 지역 : {location}
				</Card.Text>
			</Card.Body>
			<ListGroup className="list-group-flush">
				<ListGroupItem><Row><Col md={2}>⚽</Col>포지션 임시</Row></ListGroupItem>
				<ListGroupItem><Row><Col md={2}>🔖</Col>팀 임시 </Row></ListGroupItem>
				{/* <ListGroupItem><Row><Col md={2}>🔖</Col>팀 임시 </Row></ListGroupItem> */}
			
				{/* <ListGroupItem><Link to={url}><LinkStyle><Row><Col md={2}>✔</Col>상세보기</Row></LinkStyle></Link></ListGroupItem> */} 
			</ListGroup>
			{/* <Card.Body>
				<Link to={url}><LinkStyle>✔상세보기</LinkStyle></Link>
			</Card.Body> */}
		</Card>
		<br/>
		</div>
	);
};

export default UserCard;