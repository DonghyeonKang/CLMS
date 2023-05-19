import React from 'react';
import { makeStyles } from '@mui/styles';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';

const useStyles = makeStyles((theme) => ({
  modalContent: {
    backgroundColor: theme.palette.background.paper,
    padding: theme.spacing(2),
    position: 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    outline: 'none',
    width: 400,
    borderRadius: theme.shape.borderRadius,
    boxShadow: theme.shadows[5],
  },
  button: {
    marginTop: theme.spacing(2),
  },
}));

const MyModalContent = ({ onClose }) => {
  const classes = useStyles();

  return (
    <Box className={classes.modalContent}>
      <Typography variant="h6" gutterBottom>
        학교 선택
      </Typography>
      <Typography variant="body1" gutterBottom>
        학교 리스트
      </Typography>
      <Button
        variant="contained"
        color="primary"
        className={classes.button}
        onClick={onClose}
      >
        닫기
      </Button>
    </Box>
  );
}

export default MyModalContent;
