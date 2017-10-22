import moment from "moment";

export const formatCurrentDate = (nowMilliseconds) => {
    const now = new Date(nowMilliseconds);
    return moment(now).format('YYYY/MM/DD');
};


export const formatHour = (isoDate) => {
    return moment(isoDate).format('DD/MM/YYYY - hh:mm:ss');
};

export const formatDate = (isoDate) => {
    return moment(isoDate).format("DD/MM/YYYY")
};

export const getMonth = (date) => {
    return moment(date).format("MMM").toLocaleUpperCase();
};

export const getDate = (date) => {
    return date.getDate()
};