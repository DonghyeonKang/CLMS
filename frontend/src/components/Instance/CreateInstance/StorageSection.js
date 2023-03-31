import styled from "styled-components";

const StorageSection = ({setData, data}) => {
  const storageHandler = (event) => {
    setData({...data, storage: event.target.value});
  };
    return (
        <Storage>
            <Title>스토리지 구성</Title>
            <select name="storage" onChange={storageHandler} defaultValue={data.storage}>
                <option value='1'>1GB</option>
                <option value='2'>2GB</option>
                <option value='4'>4GB</option>
                <option value='8'>8GB</option>
                <option value='16'>16GB</option>
                <option value='32'>32GB</option>
            </select>
        </Storage>
    );
};

export default StorageSection;

const Storage = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  width: 40%;
  min-width: 400px;
  padding: 2%;
  margin-bottom: 5%;
  box-shadow: 2px 2px #dbdfe0;
  background-color: #fafafa;
`;

const Title = styled.div`
  margin-bottom: 5%;
`;