import * as dayjs from 'dayjs';

export interface ICheckinTz {
  id?: number;
  guestId?: number | null;
  account?: string | null;
  hoteltime?: dayjs.Dayjs | null;
  indatetime?: dayjs.Dayjs | null;
  residefate?: number | null;
  gotime?: dayjs.Dayjs | null;
  empn?: string | null;
  roomn?: string;
  rentp?: string;
  protocolrent?: number | null;
  remark?: string | null;
  phonen?: string | null;
  empn2?: string | null;
  memo?: string | null;
  lfSign?: string | null;
  guestname?: string | null;
  bc?: string | null;
  roomtype?: string | null;
}

export class CheckinTz implements ICheckinTz {
  constructor(
    public id?: number,
    public guestId?: number | null,
    public account?: string | null,
    public hoteltime?: dayjs.Dayjs | null,
    public indatetime?: dayjs.Dayjs | null,
    public residefate?: number | null,
    public gotime?: dayjs.Dayjs | null,
    public empn?: string | null,
    public roomn?: string,
    public rentp?: string,
    public protocolrent?: number | null,
    public remark?: string | null,
    public phonen?: string | null,
    public empn2?: string | null,
    public memo?: string | null,
    public lfSign?: string | null,
    public guestname?: string | null,
    public bc?: string | null,
    public roomtype?: string | null
  ) {}
}

export function getCheckinTzIdentifier(checkinTz: ICheckinTz): number | undefined {
  return checkinTz.id;
}
