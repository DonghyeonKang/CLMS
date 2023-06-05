import styled from "styled-components";
import Header from "../components/Header";
import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import Image1 from "../img/IMAGE1.png";
import Image2 from "../img/IMAGE2.png";
import Image3 from "../img/IMAGE3.png";
import Image4 from "../img/IMAGE4.png";
import Image5 from "../img/IMAGE5.png";
import Footer from "../components/Footer";

const Main = () => {
  const settings = {
    dots: false,
    lazyLoad: true,
    infinite: true,
    autoplay: true,
    speed: 500,
    slidesToShow: 1,
    slidesToScroll: 1,
    initialSlide: 2,
    arrows: false,
  };

  return (
    <>
      <Header />
      <Content>
        <SliderWrapper>
        <Slider {...settings}>
            <div>
            <SlideImage 
              src={Image1}
              alt="광고1"
            /></div>
            <div>
            <SlideImage 
              src={Image2}
              alt="광고2"
            /></div>
            <div>
            <SlideImage 
              src={Image3}
              alt="광고3"
            /></div>
            <div>
            <SlideImage 
              src={Image4}
              alt="광고4"
            /></div>
            <div>
            <SlideImage 
              src={Image5}
              alt="광고5"
            /></div>            
        </Slider>
        </SliderWrapper>

        
      </Content>
      <Footer/>
    </>
  );
};

const Content = styled.div`
  padding-top: 55px;
  width: 100%;
  min-height: 80vh;
  margin-bottom: 120px;
`;
const SlideImage = styled.img`
  width: 100%;
  height: auto;
`;
const SliderWrapper = styled.div`
  width: 100%;
`;

export default Main;
