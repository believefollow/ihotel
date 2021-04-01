jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { AccbillnoService } from '../service/accbillno.service';
import { IAccbillno, Accbillno } from '../accbillno.model';

import { AccbillnoUpdateComponent } from './accbillno-update.component';

describe('Component Tests', () => {
  describe('Accbillno Management Update Component', () => {
    let comp: AccbillnoUpdateComponent;
    let fixture: ComponentFixture<AccbillnoUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let accbillnoService: AccbillnoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [AccbillnoUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(AccbillnoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AccbillnoUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      accbillnoService = TestBed.inject(AccbillnoService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const accbillno: IAccbillno = { id: 456 };

        activatedRoute.data = of({ accbillno });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(accbillno));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const accbillno = { id: 123 };
        spyOn(accbillnoService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ accbillno });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: accbillno }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(accbillnoService.update).toHaveBeenCalledWith(accbillno);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const accbillno = new Accbillno();
        spyOn(accbillnoService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ accbillno });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: accbillno }));
        saveSubject.complete();

        // THEN
        expect(accbillnoService.create).toHaveBeenCalledWith(accbillno);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const accbillno = { id: 123 };
        spyOn(accbillnoService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ accbillno });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(accbillnoService.update).toHaveBeenCalledWith(accbillno);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
