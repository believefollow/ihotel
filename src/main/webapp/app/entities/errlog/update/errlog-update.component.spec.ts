jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ErrlogService } from '../service/errlog.service';
import { IErrlog, Errlog } from '../errlog.model';

import { ErrlogUpdateComponent } from './errlog-update.component';

describe('Component Tests', () => {
  describe('Errlog Management Update Component', () => {
    let comp: ErrlogUpdateComponent;
    let fixture: ComponentFixture<ErrlogUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let errlogService: ErrlogService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ErrlogUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(ErrlogUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ErrlogUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      errlogService = TestBed.inject(ErrlogService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const errlog: IErrlog = { id: 456 };

        activatedRoute.data = of({ errlog });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(errlog));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const errlog = { id: 123 };
        spyOn(errlogService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ errlog });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: errlog }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(errlogService.update).toHaveBeenCalledWith(errlog);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const errlog = new Errlog();
        spyOn(errlogService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ errlog });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: errlog }));
        saveSubject.complete();

        // THEN
        expect(errlogService.create).toHaveBeenCalledWith(errlog);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const errlog = { id: 123 };
        spyOn(errlogService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ errlog });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(errlogService.update).toHaveBeenCalledWith(errlog);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
