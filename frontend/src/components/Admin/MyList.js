import React from 'react';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import Checkbox from '@mui/material/Checkbox';
import IconButton from '@mui/material/IconButton';

export default function MyList(props) {
  const { items, checked, handleToggle } = props;

  const handleDelete = (index) => () => {
    const newList = items.filter((item, i) => i !== index);
    props.onDelete(newList);
  };

  return (
    <List sx={{ width: '100%', maxWidth: 400, maxHeight: 500, overflow: 'auto', bgcolor: 'background.paper' }}>
      {items.map((value, index) => {
        const labelId = `checkbox-list-label-${index}`;

        return (
          <ListItem
            key={index}
            secondaryAction={
              <IconButton edge="end" aria-label="delete" onClick={handleDelete(index)}>
              </IconButton>
            }
            disablePadding
            selected={checked.indexOf(index) !== -1}
          >
            <ListItemButton role={undefined} onClick={handleToggle(index)} dense>
              <ListItemIcon>
                <Checkbox
                  edge="start"
                  checked={checked.indexOf(index) !== -1}
                  tabIndex={-1}
                  disableRipple
                  inputProps={{ 'aria-labelledby': labelId }}
                />
              </ListItemIcon>
              <ListItemText id={labelId} primary={`${value}`} />
            </ListItemButton>
          </ListItem>
        );
      })}
    </List>
  );
}
