jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ClogService } from '../service/clog.service';
import { IClog, Clog } from '../clog.model';

import { ClogUpdateComponent } from './clog-update.component';

describe('Component Tests', () => {
  describe('Clog Management Update Component', () => {
    let comp: ClogUpdateComponent;
    let fixture: ComponentFixture<ClogUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let clogService: ClogService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ClogUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(ClogUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ClogUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      clogService = TestBed.inject(ClogService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const clog: IClog = { id: 456 };

        activatedRoute.data = of({ clog });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(clog));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const clog = { id: 123 };
        spyOn(clogService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ clog });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: clog }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(clogService.update).toHaveBeenCalledWith(clog);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const clog = new Clog();
        spyOn(clogService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ clog });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: clog }));
        saveSubject.complete();

        // THEN
        expect(clogService.create).toHaveBeenCalledWith(clog);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const clog = { id: 123 };
        spyOn(clogService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ clog });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(clogService.update).toHaveBeenCalledWith(clog);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
