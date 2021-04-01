import * as dayjs from 'dayjs';

export interface IDCk {
  id?: number;
  depot?: string;
  ckdate?: dayjs.Dayjs;
  ckbillno?: string;
  deptname?: string | null;
  jbr?: string | null;
  remark?: string | null;
  spbm?: string;
  spmc?: string;
  unit?: string | null;
  price?: number | null;
  sl?: number | null;
  je?: number | null;
  memo?: string | null;
  flag?: number | null;
  dbSign?: number | null;
  cktype?: string | null;
  empn?: string | null;
  lrdate?: dayjs.Dayjs | null;
  kcid?: number | null;
  f1?: string | null;
  f2?: string | null;
  f1empn?: string | null;
  f2empn?: string | null;
  f1sj?: dayjs.Dayjs | null;
  f2sj?: dayjs.Dayjs | null;
}

export class DCk implements IDCk {
  constructor(
    public id?: number,
    public depot?: string,
    public ckdate?: dayjs.Dayjs,
    public ckbillno?: string,
    public deptname?: string | null,
    public jbr?: string | null,
    public remark?: string | null,
    public spbm?: string,
    public spmc?: string,
    public unit?: string | null,
    public price?: number | null,
    public sl?: number | null,
    public je?: number | null,
    public memo?: string | null,
    public flag?: number | null,
    public dbSign?: number | null,
    public cktype?: string | null,
    public empn?: string | null,
    public lrdate?: dayjs.Dayjs | null,
    public kcid?: number | null,
    public f1?: string | null,
    public f2?: string | null,
    public f1empn?: string | null,
    public f2empn?: string | null,
    public f1sj?: dayjs.Dayjs | null,
    public f2sj?: dayjs.Dayjs | null
  ) {}
}

export function getDCkIdentifier(dCk: IDCk): number | undefined {
  return dCk.id;
}
