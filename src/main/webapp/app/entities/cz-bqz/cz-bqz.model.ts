import * as dayjs from 'dayjs';

export interface ICzBqz {
  id?: number;
  rq?: dayjs.Dayjs | null;
  qSl?: number | null;
  qKfl?: number | null;
  qPjz?: number | null;
  qYsfz?: number | null;
  qSjfz?: number | null;
  qFzcz?: number | null;
  qPjzcz?: number | null;
  bSl?: number | null;
  bKfl?: number | null;
  bPjz?: number | null;
  bYsfz?: number | null;
  bSjfz?: number | null;
  bFzcz?: number | null;
  bPjzcz?: number | null;
  zSl?: number | null;
  zKfl?: number | null;
  zPjz?: number | null;
  zYsfz?: number | null;
  zSjfz?: number | null;
  zFzcz?: number | null;
  zPjzcz?: number | null;
  zk?: number | null;
}

export class CzBqz implements ICzBqz {
  constructor(
    public id?: number,
    public rq?: dayjs.Dayjs | null,
    public qSl?: number | null,
    public qKfl?: number | null,
    public qPjz?: number | null,
    public qYsfz?: number | null,
    public qSjfz?: number | null,
    public qFzcz?: number | null,
    public qPjzcz?: number | null,
    public bSl?: number | null,
    public bKfl?: number | null,
    public bPjz?: number | null,
    public bYsfz?: number | null,
    public bSjfz?: number | null,
    public bFzcz?: number | null,
    public bPjzcz?: number | null,
    public zSl?: number | null,
    public zKfl?: number | null,
    public zPjz?: number | null,
    public zYsfz?: number | null,
    public zSjfz?: number | null,
    public zFzcz?: number | null,
    public zPjzcz?: number | null,
    public zk?: number | null
  ) {}
}

export function getCzBqzIdentifier(czBqz: ICzBqz): number | undefined {
  return czBqz.id;
}
