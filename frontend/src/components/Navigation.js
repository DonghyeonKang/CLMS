import { useLocation, useNavigate, useParams } from "react-router-dom";
import styled from "styled-components";

//관리자는 조건문으로 대시보드 앞에 서버 리소스 페이지 추가

const Navigation = () => {
    const param = useParams();
    const navigate = useNavigate();
    const location = useLocation();
    return (
        <>
            <Nav>
                {/*관리자면 <Link onClick={()=>navigate('/serverResource')}>서버 리소스</Link>*/}
                <Link onClick={() => navigate('/dashboard')}>대시보드</Link>
                {location.pathname.split('/')[2] ? 
                (<>
                    <span> ❯ </span>
                    {location.pathname.split('/')[2] === 'createInstance' ? <span>인스턴스 생성</span> : (
                        <Link onClick={() => navigate(`/dashboard/${param?.instanceId}`)}>{location.pathname.split('/')[2]}</Link>)
                        }
                </>)
                 : (<></>)}
                 {location.pathname.split('/')[3] ? 
                (<>
                    <span> ❯ </span>
                    <Link onClick={() => navigate(`/dashboard/${param?.instanceId}/${param?.securityGroupId}`)}>{location.pathname.split('/')[3]}</Link>
                </>)
                 : (<></>)}
                 {location.pathname.split('/')[4] ? 
                (<>
                    <span> ❯ </span>
                    <span>인바운드 규칙</span>
                </>)
                 : (<></>)}
            </Nav>
        </>
    );
};

export default Navigation;

const Nav = styled.div`
    margin: 2% 0;
`;

const Link = styled.span`
    cursor: pointer;
    &:hover{
        text-decoration: underline;
    }
`;